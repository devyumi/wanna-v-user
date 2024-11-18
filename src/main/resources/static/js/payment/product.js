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
});