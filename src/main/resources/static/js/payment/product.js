import {formatPrice, formatPriceElements} from "/js/common/format.js";

// 초기 데이터 설정
const paymentItem = {
  point: 10000,
  productList: [
    {
      productId: 2,
      productName: "채소 랩 샌드위치",
      productImage: "https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg",
      productFinalPrice: 6375,
      quantity: 2,
    },
    {
      productId: 21,
      productName: "단호박 리조또",
      productImage: "https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg",
      productFinalPrice: 8500,
      quantity: 8,
    },
  ],
  couponList: [
    {
      couponId: 3,
      eventTitle: "11월을 시작하는 이벤트 쿠폰! 11월을 시작하는 이벤트 쿠폰!",
      couponCode: 2024110803,
      couponType: 'F',
      discountAmount: 2500,
      discountRate: 0,
      eventEndDate: "2024-11-13",
    },
    {
      couponId: 2,
      eventTitle: "11월을 마무리하는 이벤트 쿠폰! 11월을 마무리하는 이벤트 쿠폰!",
      couponCode: 2024110802,
      couponType: 'P',
      discountAmount: 0,
      discountRate: 13,
      eventEndDate: "2024-11-30",
    },
    {
      couponId: 1,
      eventTitle: "아무 이유 없다. 그냥 주는대로 받아라 쿠폰!",
      couponCode: 2024110801,
      couponType: 'F',
      discountAmount: 1000,
      discountRate: 0,
      eventEndDate: "2024-11-15",
    },
  ],
};

const totalPrice = paymentItem.productList.reduce((total, item) => {
  return total + (item.productFinalPrice * item.quantity);
}, 0);

formatPriceElements();

/**
 * 주소 찾기 API
 */
document.getElementById('find-address-btn').addEventListener("click",
    function execDaumPostcode() {
      console.log("다음 주소 찾기 연결");
      new daum.Postcode({
        oncomplete: function (data) {
          document.getElementById('address').value = data.address + " ("
              + data.zonecode + ")";
        }
      }).open();
    });

/**
 * 결제 상품 데이터
 */
const productList = paymentItem.productList;
const productContainer = document.querySelector("#product-container");

productContainer.innerHTML = '';

productList.forEach(item => {
  const productItem = document.createElement('div');
  productItem.classList.add('product-item');

  productItem.innerHTML = `
    <div class="img-container">
      <img src="${item.productImage}" alt="Product image"/>
    </div>
    <div class="product-info-container">
      <p class="product-name">${item.productName}</p>
      <div class="price-container">
        <span class="product-quantity">수량 ${item.quantity}개</span>
        <span class="final-price price" data-price="${item.productFinalPrice
  * item.quantity}">${(item.productFinalPrice
      * item.quantity).toLocaleString()} 원</span>
      </div>
    </div>
  `;

  productContainer.appendChild(productItem);
});

/**
 * 적용 가능한 쿠폰 데이터
 */
const couponList = paymentItem.couponList;
const couponContainer = document.querySelector("#coupon-container");

couponContainer.innerHTML = '';

couponList.forEach(item => {
  const couponItem = document.createElement('div');
  couponItem.classList.add('coupon-item');

  couponItem.innerHTML = `
    <div class="coupon-info-container">
      <h5 class="coupon-discount-price">${item.couponType === 'F' ? formatPrice(
      item.discountAmount) : item.discountRate + '%'}</h5>
      <p class="event-name">${item.eventTitle}</p>
      <p class="event-period">~ ${item.eventEndDate} 까지</p>
    </div>
    <div class="btn-select" data-coupon-id="${item.couponId}">선택</div>
  `;

  couponContainer.appendChild(couponItem);
});

/**
 * 쿠폰 선택 시 실행될 함수
 */
function chooseCoupon(couponId) {

  // Offcanvas 닫기
  const offcanvasElement = document.getElementById('offcanvasCart');
  const offcanvas = bootstrap.Offcanvas.getInstance(offcanvasElement);
  if (offcanvas) {
    offcanvas.hide();
  }

  const selectedCoupon = paymentItem.couponList.find(
      coupon => {
        return coupon.couponId === parseInt(couponId);
      });

  console.log("selectedCoupon: " + selectedCoupon)

  /**
   * 쿠폰 할인 금액 계산
   * 정률 쿠폰 - 10의 자리에서 올림
   * @type {number}
   */
  let discountAmount = 0;
  if (selectedCoupon) {
    if (selectedCoupon.couponType === 'F') {
      discountAmount = selectedCoupon.discountAmount;
    } else if (selectedCoupon.couponType === 'P') {
      discountAmount = Math.ceil(
          (totalPrice * (selectedCoupon.discountRate / 100)) / 10) * 10;  // 10의 자리에서 올림
    }
  }

  document.getElementById(
      "applied-coupon-amount").innerText = formatPrice(discountAmount);

  // 최종 결제 금액 업데이트
  const pointValue = parseInt(pointInput.value.replace(/[^0-9]/g, "")) || 0;

  document.getElementById('final-payment-amount').innerText =
      calculateFinalPaymentAmount(discountAmount, pointValue);

}

/**
 * 쿠폰 선택 버튼에 이벤트 리스너 추가
 */
const couponButtons = document.querySelectorAll('.btn-select');
couponButtons.forEach(button => {
  button.addEventListener('click', function () {
    const couponId = this.getAttribute('data-coupon-id');
    chooseCoupon(couponId);
  });
});

/**
 * 보유 포인트
 */
document.getElementById('points-balance').innerText = '보유 ' + formatPrice(
    paymentItem.point);

/**
 * 사용 포인트 입력 시 최대 포인트 제한
 */
const pointInput = document.getElementById('used-points'); // 포인트 입력 값
const maxPoint = paymentItem.point; // 보유 포인트 값

pointInput.addEventListener('input', function () {
  // 1. 숫자만 추출
  let rawValue = pointInput.value.replace(/[^0-9]/g, "");

  // 2. 포인트 값이 보유 포인트를 초과하지 않도록 처리
  if (parseInt(rawValue) > maxPoint) {
    rawValue = maxPoint.toString(); // 최대값으로 제한
  }

  // 3. 쉼표 포맷팅
  const formattedValue = new Intl.NumberFormat("ko-KR").format(rawValue);

  // 4. 포맷팅된 값을 input 필드에 다시 적용
  pointInput.value = formattedValue;

  // 5. 최종 결제 금액 업데이트
  const pointValue = parseInt(rawValue) || 0;
  const couponAmount = parseInt(
      document.getElementById("applied-coupon-amount").innerText.replace(
          /[^0-9]/g, "")) || 0;

  document.getElementById('final-payment-amount').innerText =
      calculateFinalPaymentAmount(couponAmount, pointValue);
});

/**
 * 최종 결제 금액 계산
 * {상품별 합계} - {적용된 쿠폰 할인액} - {적용된 포인트 사용액}
 */
function calculateFinalPaymentAmount(couponAmount = 0, point = 0) {
  const finalAmount = totalPrice - couponAmount - point;
  return formatPrice(finalAmount > 0 ? finalAmount : 0); // 0보다 작은 값 방지
}

document.getElementById(
    'final-payment-amount').innerText = calculateFinalPaymentAmount();

/**
 * toss 결제를 위한 orderId 생성
 */
// document.getElementById('payment-button').addEventListener("click",
//     async function () {
//       try {
//         const response = await axios.post(
//             '/api/v1/payment/generate-order-id');
//         const data = response.data.data;
//
//         console.log("data: " + data);
//
//         // orderId를 이용해 Toss 결제 준비 API로 요청 보내기
//         const paymentUrl = await requestTossPayment(data);
//
//         // 결제 URL로 리디렉션
//         window.location.href = paymentUrl;
//
//         // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
//         // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
//         // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
//         // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
//         await widgets.requestPayment({
//           orderId: data.orderId,
//           orderName: "토스 테스트1",
//           successUrl: data.successUrl,
//           failUrl: data.failUrl,
//           customerEmail: "toss1@gmail.com",
//           customerName: "김토스",
//           // 가상계좌 안내, 퀵계좌이체 휴대폰 번호 자동 완성에 사용되는 값입니다. 필요하다면 주석을 해제해 주세요.
//           customerMobilePhone: "01012341234",
//         });
//
//       } catch (error) {
//         console.error('주문 번호 생성에 실패했습니다:', error);
//       }
//     })

/**
 * toss 결제
 */
document.addEventListener("DOMContentLoaded", async () => {
  try {
    // 서버에서 클라이언트 키와 기본 데이터를 받아옴
    const response = await axios.post('/api/v1/payment/generate-order-id');
    const data = response.data.data;

    /**
     * ------  결제위젯 초기화 ------
     * TODO: clientKey는 개발자센터의 결제위젯 연동 키 > 클라이언트 키로 바꾸세요.
     * TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요. 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
     * @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
     */
    const clientKey = data.clientKey;
    const tossPayments = TossPayments(clientKey);
    const customerKey = generateRandomString();

    /**
     * 회원 결제
     * @docs https://docs.tosspayments.com/sdk/v2/js#tosspaymentswidgets
     */
    const widgets = tossPayments.widgets({customerKey});

    /**
     * 비회원 결제
     */
    // const widgets = tossPayments.widgets({customerKey: TossPayments.ANONYMOUS});

    /**
     * 기본 결제 금액 설정
     */
    const amount = getCurrentAmount();
    await widgets.setAmount(amount);

    /**
     * 결제 및 이용약관 UI 렌더링
     */
    await Promise.all([
      /**
       *  ------  결제 UI 렌더링 ------
       *  @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
       */
      widgets.renderPaymentMethods({
        selector: "#payment-method",

        /**
         * 렌더링하고 싶은 결제 UI의 variantKey
         * 결제 수단 및 스타일이 다른 멀티 UI를 직접 만들고 싶다면 계약이 필요해요.
         * @docs https://docs.tosspayments.com/guides/v2/payment-widget/admin#새로운-결제-ui-추가하기
         */
        variantKey: "DEFAULT"
      }),

      /**
       * ------  이용약관 UI 렌더링 ------
       * @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderagreement
       */
      widgets.renderAgreement({
        selector: "#agreement",
        variantKey: "AGREEMENT",
      }),
    ]);

    /**
     * ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
     * @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
     */
    document.getElementById("payment-button").addEventListener("click",
        async () => {
          const orderIdResponse = await axios.post(
              '/api/v1/payment/generate-order-id');
          const orderData = orderIdResponse.data.data;

          /**
           * ------  주문서의 결제 금액 설정 ------
           * TODO: 위젯의 결제금액을 결제하려는 금액으로 초기화하세요.
           * TODO: renderPaymentMethods, renderAgreement, requestPayment 보다 반드시 선행되어야 합니다.
           * @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount
           */
          const updatedAmount = getCurrentAmount();
          await widgets.setAmount(updatedAmount);
          /**
           * 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
           * 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
           */
          await widgets.requestPayment({
            orderId: orderData.orderId,
            orderName: "토스 테스트1",
            successUrl: orderData.successUrl,
            failUrl: orderData.failUrl,
            customerEmail: "toss1@gmail.com",
            customerName: "김토스",
            customerMobilePhone: "01012341234",
          });
        });
  } catch (error) {
    console.error("초기화 중 오류 발생:", error);
  }
});

/**
 * 현재 결제 금액 가져오기
 */
function getCurrentAmount() {
  return {
    currency: "KRW",
    value: parseInt(
        document.getElementById("final-payment-amount").textContent.replace(
            /[^0-9]/g, ""),
        10
    ),
  };
}

/**
 * 랜덤 문자열 생성
 */
function generateRandomString() {
  return window.btoa(Math.random()).slice(0, 20);
}