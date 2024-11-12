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


});