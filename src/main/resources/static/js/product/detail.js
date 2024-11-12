document.addEventListener('DOMContentLoaded', function () {
  async function productDetail(productId) {
    const response = await fetch(`/api/product?id=${productId}`);
    console.log("productId: " + productId)
    const jsonData = await response.json();
    const data = jsonData.data;

    const nameElements = document.getElementsByClassName("product-name");
    for (let i = 0; i < nameElements.length; i++) {
      nameElements[i].textContent = data.name;
    }

    document.getElementById("product-image").src = data.image;
    document.getElementById("discount-rate").textContent = data.discountRate + "%";
    document.getElementById("selling-price").textContent = new Intl.NumberFormat("ko-KR").format(data.sellingPrice) + "원";
    document.getElementById("final-price").textContent = new Intl.NumberFormat("ko-KR").format(data.finalPrice) + "원";
    document.getElementById("product-description").src = data.description.description;
    document.getElementById("product-stock").textContent = data.stock;

  }

  productDetail(productId);

  const decreaseBtn = document.getElementById("decrease-btn");
  const quantityInput = document.getElementById("quantity-input");
  const increaseBtn = document.getElementById("increase-btn");
  const QUANTITY_MIN = 1;
  const QUANTITY_MAX = 99;

  // 장바구니 담기 수량 감소 버튼
  decreaseBtn.addEventListener("click", () => {
    let currentValue = parseInt(quantityInput.value);
    if (currentValue > QUANTITY_MIN) {
      quantityInput.value = currentValue - 1;
    }
  })

  // 장바구니 담기 수량 증가 버튼
  increaseBtn.addEventListener("click", () => {
    let currentValue = parseInt(quantityInput.value);
    if (currentValue < QUANTITY_MAX) {
      quantityInput.value = currentValue + 1;
    }
  })
});