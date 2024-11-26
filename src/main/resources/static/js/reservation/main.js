$(function() {
    let selectedDate = '';
    let selectedTime = '';
    let selectedGuest = '';

    rome(inline_cal, { time: false }).on('data', function(value) {
        selectedDate = value.split(' ')[0];
        console.log('선택한 날짜:', selectedDate);

        $("#person-buttons").empty();
        $(".rectangle-button").removeClass('selected');
        selectedTime = '';
        selectedGuest = '';

        fetchReservationData(selectedDate, restaurantId);
    });

    function fetchReservationData(date, restaurantId) {
        $.ajax({
            url: `/api/reservation/date`,
            type: 'GET',
            data: { selectDate: date, restaurantId: restaurantId },
            success: function(response) {
                $("#reservation-time").html('<h2 style="padding-top: 40px;">예약 날짜</h2>');

                let timeButton = '';

                let currentDateTime = new Date();
                console.log("현재 날짜 및 시간:", currentDateTime);

                let formattedDate = currentDateTime.toLocaleDateString('en-CA'); // 'YYYY-MM-DD' 형식
                console.log("현재 날짜:", formattedDate);

                let hours = currentDateTime.getHours().toString().padStart(2, '0');
                let minutes = currentDateTime.getMinutes().toString().padStart(2, '0');
                let formattedTime = `${hours}:${minutes}`;
                console.log("현재 시간:", formattedTime);


                if (!response.reservationTimes || response.reservationTimes.length === 0 || formattedDate > response.reservationDate)
                    timeButton = '<h5 style="font-size: 15px">예약할 수 없습니다.</h5>';
                else {
                    response.reservationTimes.forEach(function(time) {
                        if(formattedDate <= selectedDate) {
                            timeButton += `<button type="button" class="rectangle-button" data-time="${time}">${time}</button>`;
                        }
                        else
                            timeButton += `<button type="button" class="rectangle-button" data-time="${time}" style="display: none">${time}</button>`;
                    });
                }

                $("#time-buttons").replaceWith(`<div class="button-slider" id="time-buttons">${timeButton}</div>`);

                if (selectedTime)
                    $(`.rectangle-button[data-time="${selectedTime}"]`).addClass('selected');
            },
            error: function(error) {
                console.error('AJAX 요청 실패:', error);
            }
        });
    }

    $(document).on('click', '.rectangle-button', function() {
        selectedGuest = '';

        selectedTime = $(this).data('time');
        console.log('선택된 시간:', selectedTime);

        $(".rectangle-button").removeClass('selected');
        $(this).addClass('selected');

        $.ajax({
            url: '/api/reservation/time',
            type: 'GET',
            data: {
                selectDate: selectedDate,
                selectTime: selectedTime,
                restaurantId: restaurantId
            },
            success: function(response) {
                $("#reservation-guest").html('<h2 style="padding-top: 40px;">인원 수</h2>');
                let guestAccount = response.guestAccount;

                if(guestAccount > 8)
                    guestAccount = 8;

                let personButton = '';

                if (guestAccount === 0)
                    personButton = '<h5 style="font-size: 15px;">예약할 수 없습니다.</h5>';
                else {
                    for (let i = 1; i <= guestAccount; i++)
                        personButton += `<button type="button" class="circle-button">${i}</button>`;
                }

                $("#person-buttons").html(personButton);
            },
            error: function(error) {
                console.error('AJAX 요청 실패:', error);
            }
        });
    });

    $(document).on('click', '.circle-button', function() {
        selectedGuest = $(this).text();
        if (selectedTime) {
            $(".rectangle-button").removeClass('selected');
            $(`.rectangle-button[data-time="${selectedTime}"]`).addClass('selected');
        }
    });

    $('#submitReservation').on('click', function(e) {
        $.ajax({
            url: '/api/reservation/confirm',
            type: 'POST',
            data: {
                selectDate: selectedDate,
                selectTime: selectedTime,
                restaurantId: restaurantId,
                selectGuest: selectedGuest
            },
            success: function(response) {
                if (response.status === 'success')
                    window.location.href = '/checkout/success';
                else if (response.status === 'payment') {
                    const reservationId = response.reservation.reservationId;
                    window.location.href = `/payment/reservation/${reservationId}`;
                } else
                    alert(response.message);
            },
            error: function(xhr, status, error) {
                alert(xhr.responseJSON?.message || "오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});