import {formatPriceElements} from "/js/common/format.js";

document.addEventListener('DOMContentLoaded', function () {
  async function productDetail(productId) {
    const response = await fetch(`/api/v1/products/${productId}`);
    const jsonData = await response.json();
    const data = jsonData.data;

    const nameElements = document.getElementsByClassName("product-name");
    for (let i = 0; i < nameElements.length; i++) {
      nameElements[i].textContent = data.name;
    }

    document.getElementById("product-image").src = data.image;
    document.getElementById("discount-rate").textContent = data.discountRate
        + "%";
    document.getElementById("selling-price").textContent = formatPrice(
        data.sellingPrice);
    document.getElementById("final-price").textContent = formatPrice(
        data.finalPrice);
    document.getElementById(
        "product-description").src = data.description.description;
    document.getElementById("product-stock").textContent = data.stock;

  }

  // productDetail(productId);

  formatPriceElements();

  const productId = document.getElementById('hiddenProductId').textContent;
  const stockText = document.getElementById('product-stock').textContent;
  const stock = parseInt(stockText);  // 숫자 부분만 추출
  const decreaseBtn = document.getElementById("decrease-btn");
  const quantityInput = document.getElementById("quantity-input");
  const increaseBtn = document.getElementById("increase-btn");
  const MIN_PRODUCT_QUANTITY = 1;
  const MAX_PRODUCT_QUANTITY = 99;

  /**
   * 장바구니 담기 수량 감소 버튼
   */
  decreaseBtn.addEventListener("click", () => {
    let currentValue = parseInt(quantityInput.value);
    if (currentValue > MIN_PRODUCT_QUANTITY) {
      quantityInput.value = currentValue - 1;
    } else {
      alert("장바구니 담을 수 있는 최소 수량은 1개 입니다.");
      return;
    }
  })

  /**
   * 장바구니 담기 수량 증가 버튼
   */
  increaseBtn.addEventListener("click", () => {
    let currentValue = parseInt(quantityInput.value);
    if (currentValue >= stock) {
      alert("최대 가능 재고는 " + stock + "개 입니다.");
      return;
    } else if (currentValue >= MAX_PRODUCT_QUANTITY) {
      alert("장바구니 담을 수 있는 최대 수량은 99개 입니다.");
      return;
    }
    quantityInput.value = currentValue + 1;
  })

  /**
   * 장바구니 상품 추가
   */
  document.getElementById('add-cart-item').addEventListener("click",
      async function () {
        const currentQuantity = parseInt(quantityInput.value);
        console.log(
            "productId: " + productId + ", quantity: " + currentQuantity
            + ", stock: "
            + stock);

        // 수량이 1 이상 99 이하인지 확인
        if (currentQuantity < MIN_PRODUCT_QUANTITY || currentQuantity
            > MAX_PRODUCT_QUANTITY) {
          alert("수량은 1 이상 99 이하이어야 합니다.");
          return;
        }

        if (currentQuantity > stock) {
          alert("최대 가능 재고는 " + stock + "개 입니다.");
          return;
        }

        const addCartItemData = {
          productId: productId,
          quantity: currentQuantity
        }

        try {
          const response = await axios.post('/api/v1/cart', addCartItemData, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          console.log('장바구니 추가 성공:', response.data);
          alert('상품이 장바구니에 추가되었습니다.');

          // Offcanvas 닫기
          const offcanvasElement = document.getElementById('offcanvasBottom');
          const bsOffcanvas = bootstrap.Offcanvas.getInstance(offcanvasElement);
          if (bsOffcanvas) {
            bsOffcanvas.hide(); // Offcanvas 닫기
          }
        } catch (error) {
          console.error('장바구니 추가 실패:', error);
          alert('장바구니에 추가에 실패했습니다.');
        }
      });
});