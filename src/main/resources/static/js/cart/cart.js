import {
  formatPrice,
  formatPriceElements,
  formatNameElements
} from "/js/common/format.js";

const MIN_PRODUCT_QUANTITY = 1;
const MAX_PRODUCT_QUANTITY = 99;

formatPriceElements(); // 가격을 한국 원화(KRW) 형식으로 포맷팅
formatNameElements(); // 상품명을 말줄임표 형식으로 포맷팅
updateTotalPrice();

/**
 * 수량 감소 버튼
 * @param button
 */
document.querySelectorAll('.quantity-decrease').forEach(button => {
  button.addEventListener('click', async function () {
    const quantityInput = this.closest('.quantity-selector').querySelector(
        '.item-quantity');
    let quantity = parseInt(quantityInput.value);
    if (quantity > MIN_PRODUCT_QUANTITY) {
      quantity--;
      quantityInput.value = quantity;
      await updateQuantity(quantityInput);  // 수량 업데이트
    }
  });
});

/**
 * 수량 증가 버튼
 * @param button
 */
document.querySelectorAll('.quantity-increase').forEach(button => {
  button.addEventListener('click', async function () {
    const quantityInput = this.closest('.quantity-selector').querySelector(
        '.item-quantity');
    let quantity = parseInt(quantityInput.value);
    if (quantity < MAX_PRODUCT_QUANTITY) {
      quantity++;
      quantityInput.value = quantity;
      await updateQuantity(quantityInput);  // 수량 업데이트
    }
  });
});

/**
 * 상품 수량 직접 입력
 */
document.querySelectorAll('.item-quantity').forEach(input => {
  input.addEventListener('input', async function () {
    const quantity = parseInt(this.value);
    if (quantity >= MIN_PRODUCT_QUANTITY && quantity <= MAX_PRODUCT_QUANTITY) {
      await updateQuantity(this);  // 수량 업데이트
    } else {
      alert('수량은 1 이상 99 이하로 입력해주세요.');
      this.value = Math.min(Math.max(quantity, 1), MAX_PRODUCT_QUANTITY);  // 유효한 범위로 조정
    }
  });
});

/**
 * 장바구니 상품 수량 변경
 * @param inputElement
 */
async function updateQuantity(inputElement) {
  const quantity = parseInt(inputElement.value);
  const cartId = inputElement.getAttribute('data-id');

  console.log('수량 변경, item.id:', cartId, '변경된 수량:', quantity);

  // 수량이 1 이상 99 이하인지 확인
  if (quantity < MIN_PRODUCT_QUANTITY || quantity > MAX_PRODUCT_QUANTITY) {
    alert("수량은 1 이상 99 이하이어야 합니다.");
    return;
  }

  // 장바구니 상품 수량 업데이트 데이터 구성
  const cartUpdateData = {
    cartId: cartId,
    quantity: quantity  // 변경된 수량
  };

  try {
    // 수량 업데이트를 서버에 요청
    const response = await axios.patch(`/api/v1/cart`, cartUpdateData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });

    const productInfo = inputElement.closest('.product-info'); // 가격을 표시하는 엘리먼트 찾기
    const priceElement = productInfo.querySelector('.product-total-price'); // 최종 가격을 보여줄 요소
    const productFinalPriceElement = productInfo.querySelector(
        '.hidden-final-price');  // 개별 제품 가격

    // 제품 단가 가져오기 (hidden 상태로 보유된 값)
    const productFinalPrice = parseFloat(productFinalPriceElement.textContent);  // 제품 가격

    // 최종 가격 계산
    const totalPrice = productFinalPrice * quantity;  // 최종 가격 = 단가 * 수량
    priceElement.textContent = formatPrice(totalPrice);  // 최종 가격을 포맷하여 표시

    // 최종 가격을 data-price 속성에도 업데이트
    priceElement.setAttribute('data-price', totalPrice);

    await updateTotalPrice();  // 최종 가격을 갱신하는 함수 호출

  } catch (error) {
    // 오류 발생 시
    console.error('수량 업데이트 실패:', error);
  }
}

/**
 * 결제하는 최종 가격으로 갱신
 * @returns {Promise<void>}
 */
function updateTotalPrice() {
  const totalPriceElement = document.querySelector(
      '.total-price .total-price-amount');
  const itemPrices = document.querySelectorAll('.product-total-price');

  let total = 0;

  itemPrices.forEach(priceElement => {
    console.log("itemPrices: " + itemPrices)
    const price = parseFloat(priceElement.getAttribute('data-price'));
    if (!isNaN(price)) {
      total += price;
      console.log("total: " + total)
    } else {
      console.warn('data-price가 올바르지 않음:', priceElement);
    }
  });

  if (totalPriceElement) {
    totalPriceElement.textContent = formatPrice(total);
  }
}
