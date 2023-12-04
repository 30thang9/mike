$(document).ready(function () {
    $('#wrapper-list-product').on("click", ".btn-product-right", function () {
        var wrapper = $(this).closest(".wrapper-product");
        var id = wrapper.data("productId");
        var url = "/mike/api/product/product-single/" + id;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function (response) {
                var product = response.product;
                var productDetail = response.productDetail;
                //                var minPrice = response.minPrice;
                //                var maxPrice = response.maxPrice;
                var unique = new Set();
                var $productModelBody = $(".product-model-body");
                var $productSingle =
                    $(`
                    ${product.bestSellerStatus === 'ACTIVE' ? '<div class="ribbon ribbon-success">Best seller</div>' : ''}
                    ${product.hotStatus === 'ACTIVE' ? '<div class="ribbon ribbon-warning">Hot</div>' : ''}
                    <div class="row row-wrapper" data-product-id="${id}">
                        <div class="col-lg-6">
                            <div class="owl-carousel owl-theme owl-dots-modern detail-full" data-slider-id="1">
                                ${(productDetail?.[0]?.images || []).length === 0
                            ? `
                                        <div class="detail-full-item-modal"
                                            style="background: center center url('/images/product/${product.avatar}') no-repeat; background-size: cover;">
                                        </div>
                                    `
                            : (productDetail?.[0]?.images || []).map((image, index) => {
                                if (!unique.has(image)) {
                                    unique.add(image);
                                    return `
                                                <div class="detail-full-item-modal"
                                                    style="background: center center url('/images/product/${image}') no-repeat; background-size: cover;">
                                                </div>
                                            `;
                                }
                                return '';
                            }).join('')
                        }
                            </div>
                        </div>
                        <div class="col-lg-6 d-flex align-items-center">
                            <div>
                                <h2 class="mb-4 mt-4 mt-lg-1">${product.name}</h2>
                                <div class="d-flex flex-column flex-sm-row align-items-sm-center justify-content-sm-between mb-4">
                                    <ul class="list-inline mb-2 mb-sm-0 product-price">
                                        ${productDetail?.[0]?.discountPercent > 0.0
                            ? `<li class="list-inline-item h4 fw-light mb-0">
                                                                ${(productDetail[0].exportPrice - productDetail[0].exportPrice * productDetail[0].discountPercent / 100).toFixed(2)}
                                                            </li>
                                                            <li class="list-inline-item text-muted fw-light">
                                                                <del>
                                                                ${(productDetail[0].exportPrice).toFixed(2)}
                                                                </del>
                                                            </li>`
                            : `<li class="list-inline-item h4 fw-light mb-0">
                                                                ${(productDetail[0].exportPrice).toFixed(2)}
                                                            </li>`
                        }
                                    </ul>
                                    <div class="d-flex align-items-center">
                                        <ul class="list-inline me-2 mb-0">
                                            <li class="list-inline-item me-0"><i class="fa fa-star text-primary"></i></li>
                                            <li class="list-inline-item me-0"><i class="fa fa-star text-primary"></i></li>
                                            <li class="list-inline-item me-0"><i class="fa fa-star text-primary"></i></li>
                                            <li class="list-inline-item me-0"><i class="fa fa-star text-primary"></i></li>
                                            <li class="list-inline-item me-0"><i class="fa fa-star text-gray-300"></i></li>
                                        </ul>
                                        <span class="text-muted text-uppercase text-sm">25 reviews</span>
                                    </div>
                                </div>
                                <p class="mb-4 text-muted">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                                    ullamco
                                </p>
                                <p class="mb-4"><span class="text-muted text-danger" id="quantity-supply"></span></p>
                                <form action="#">
                                    <div class="row">
                                        <div class="col-12 detail-option mb-3">
                                            <h6 class="detail-option-heading">Colour <span>(required)</span></h6>
                                            <ul class="list-inline mb-0 colours-wrapper">
                                                ${productDetail.map((detail, index) => {
                            if (!unique.has(detail.color.name)) {
                                unique.add(detail.color.name);
                                return `
                                                            <li class="list-inline-item">
                                                                <label class="btn-colour" for="colour_${index}" style="background-color: ${detail.color.colorHex}"></label>
                                                                <input class="input-invisible input-color-modal" type="radio" name="colour"
                                                                    value="${detail.color.id}" id="colour_${index}" required>
                                                            </li>
                                                        `;
                            }
                            return '';
                        }).join('')}
                                            </ul>
                                        </div>
                                        <div class="col-12 detail-option mb-3">
                                            <h6 class="detail-option-heading">Size <span>(required)</span></h6>
                                            ${productDetail.map((detail, index) => {
                            if (!unique.has(detail.size.name)) {
                                unique.add(detail.size.name);
                                return `
                                                        <label class="btn btn-sm btn-outline-secondary detail-option-btn-label"
                                                            for="size_${index}">
                                                            ${detail.size.name}
                                                        <input class="input-invisible input-size-modal" type="radio" name="size" value="${detail.size.id}" id="size_${index}" required>
                                                    </label>
                                                    `;
                            }
                            return '';
                        }).join('')}
                                        </div>
                                        <div class="col-12 detail-option mb-5">
                                            <label class="detail-option-heading fw-bold">Items <span>(required)</span></label>
                                            <input class="form-control detail-quantity" name="items"
                                                type="number" min="1" step="1" value="1" pattern="[0-9]+" required>
                                        </div>
                                    </div>
                                    <ul class="list-inline">
                                        <li class="list-inline-item">
                                            <button class="btn btn-dark btn-lg mb-1" type="submit">
                                                <i class="fa fa-shopping-cart me-2"></i>Add to Cart
                                            </button>
                                        </li>
                                        <li class="list-inline-item">
                                            <a class="btn btn-outline-secondary mb-1" href="#">
                                                <i class="far fa-heart me-2"></i>Add to wishlist
                                            </a>
                                        </li>
                                    </ul>
                                </form>
                            </div>
                        </div>
                    </div>
                `);

                $productModelBody.empty();
                $productModelBody.append($productSingle);

                var owlCarousel = $('.owl-carousel');
                owlCarousel.owlCarousel({
                    items: 1,
                    loop: true,
                    dots: true,
                    nav: false
                });
                owlCarousel.trigger('refresh.owl.carousel');

            },
            error: function (xhr, status, error) {
                console.log('Error:', error);
            },
            complete: function () {
                $(".product-model-body").eq(0).find('.input-color-modal').eq(0).prop('checked', true);
                $(".product-model-body").eq(0).find('.input-color-modal').eq(0).trigger('change');
            }
        });
    });

    $(".product-model-body").on('change', '.input-size-modal', function () {
        $(this).closest('.detail-option').find('label').not($(this).closest('label')).removeClass('active');
        if ($(this).prop('checked')) {
            $(this).closest('label').addClass('active');
        } else {
            $(this).closest('label').removeClass('active');
        }
    });

    $(".product-model-body").on('change', '.input-color-modal', function () {
        $(this).closest('.colours-wrapper').find('label.btn-colour').not($(this).prevAll('label.btn-colour:first')).removeClass('active');
        if ($(this).prop('checked')) {
            $(this).prevAll('label.btn-colour:first').addClass('active');
        } else {
            $(this).prevAll('label.btn-colour:first').removeClass('active');
        }
    });

});

$(document).ready(function () {
    function updateProductState(productDetail) {
        var $inputSizeProduct = $('.input-size-modal');
        $('.input-size-modal:checked').closest('label').removeClass('active');
        $('.input-size-modal:checked').prop('checked', false);
        var unique1 = new Set();

        productDetail.map((detail, index) => {
            if (!unique1.has(detail.size.id) && detail.quantity > 0 && detail.productDetailStatus === "ACTIVE") {
                unique1.add(detail.size.id);
            }
        });

        $inputSizeProduct.each(function () {
            const id = parseInt($(this).val().trim());
            if (unique1.size > 0 && unique1.has(id)) {
                $(this).closest('label.detail-option-btn-label').removeClass('soldout');
            } else {
                $(this).closest('label.detail-option-btn-label').addClass('soldout');
            }
        });

        var owlCarousel = $('.owl-carousel');
        var $carousel_item =
            $(`
                ${(productDetail?.[0]?.images || []).length === 0
                    ? `
                        <div class="detail-full-item-modal"
                            style="background: center center url('/images/product/${product.avatar}') no-repeat; background-size: cover;">
                        </div>
                    `
                    : (productDetail?.[0]?.images || []).map((image, index) => {
                        return `
                            <div class="detail-full-item-modal"
                                style="background: center center url('/images/product/${image}') no-repeat; background-size: cover;">
                            </div>
                        `;
                    }).join('')
                }
            `);

        owlCarousel.trigger('destroy.owl.carousel');
        $(owlCarousel).empty();
        $(owlCarousel).append($carousel_item);

        owlCarousel.owlCarousel({
            items: 1,
            loop: true,
            dots: true,
            nav: false
        });

        $('#quantity-supply').text('');

        var productPrice = $('.product-price');
        var $priceChild =
            $(`
                ${productDetail?.[0]?.discountPercent > 0.0
                    ? `<li class="list-inline-item h4 fw-light mb-0">
                            ${(productDetail[0].exportPrice - productDetail[0].exportPrice * productDetail[0].discountPercent / 100).toFixed(2)}
                        </li>
                        <li class="list-inline-item text-muted fw-light">
                            <del>
                                ${(productDetail[0].exportPrice).toFixed(2)}
                            </del>
                        </li>`
                    : `<li class="list-inline-item h4 fw-light mb-0">
                            ${(productDetail[0].exportPrice).toFixed(2)}
                        </li>`
                }
            `);
        $(productPrice).empty();
        $(productPrice).append($priceChild);

    }

    $('.product-model-body').on('change', '.input-color-modal', function () {
        if ($(this).prop('checked')) {
            var productId = $(this).closest('div.row-wrapper').data('product-id');
            const colorId = $(this).val();
            var url = "/mike/api/product/product-color-single?productId=" + productId + "&colorId=" + colorId;
            $.ajax({
                url: url,
                type: "GET",
                dataType: "JSON",
                success: function (data) {
                    var productDetail = data.productDetail;
                    var product = data.product;

                    updateProductState(productDetail);
                },
                error: function (jqXHR, textStatus, errorTh) {
                    // Xử lý lỗi nếu cần
                }
            });
        }
    });

    $('.product-model-body').on('change', '.input-size-modal', function () {
        if ($(this).prop('checked')) {
            var href = window.location.href;
            var productId = $(this).closest('div.row-wrapper').data('product-id');
            var colorId = $('.input-color-modal:checked').val();
            var sizeId = $(this).val();
            var url = "/mike/api/product/product-size-color-single?productId=" + productId + "&colorId=" + colorId + "&sizeId=" + sizeId;
            $.ajax({
                url: url,
                type: "GET",
                dataType: "JSON",
                success: function (data) {
                    var productDetail = data.productDetail;
                    if (productDetail.length === 1) {
                        var quantity = productDetail[0].quantity;
                        var txt = "Quantity can be supplied: " + quantity;
                        $('#quantity-supply').text(txt);
                    }
                },
                error: function (jqXHR, textStatus, errorTh) {

                }
            });
        }
    });
});

$(document).ready(function () {
    $('.product-model-body').on('submit', 'form', function (e) {
        e.preventDefault();

        var productId = $(this).closest('div.row-wrapper').data('product-id');
        var sizeId = $(this).find('input.input-size-modal').filter(':checked').val().trim();
        var colorId = $(this).find('input.input-color-modal').filter(':checked').val().trim();
        var quantity = $(this).find('input.detail-quantity').val().trim();
        var price = 250000;

        var formData =
        {
            id:
            {
                productId: productId,
                sizeId: sizeId,
                colorId: colorId
            },
            quantity: quantity,
            price: price
        }

        var url = "/mike/api/cart/add";
        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function (data) {
                showModal("Add to cart is successful!", true);
                updateCart(data);
                $(".product-model-body").eq(0).find('.input-color-modal').eq(0).prop('checked', true);
                $(".product-model-body").eq(0).find('.input-color-modal').eq(0).trigger('change');
                $(".product-model-body").eq(0).find('input.detail-quantity').eq(0).val('1');
            },
            error: function (xhr, status, error) {
                console.log(xhr.responseText);
                console.log(status);
                console.log(error);
                showModal("Add to cart is failed!", false);
            }
        });
    });

    function showModal(message, isSuccess) {
        Swal.fire({
            title: message,
            icon: isSuccess ? 'success' : 'error',
            timer: 2000,
            timerProgressBar: true,
            showConfirmButton: false
        });

        const progressBar = $('.swal2-popup .swal2-timer-progress-bar').eq(0);
        if (!isSuccess) {
            progressBar.css('background-color', '#ff3e1d');
        } else {
            progressBar.css('background-color', '#71dd37');
        }
    }

    function updateCart(data) {
        var cart = data.cart;
        var totalAmount = data.totalAmount;
        var totalQuantity = data.totalQuantity;

        var $headerCartWrapper = $('#header-cart-wrapper');
        var $headerCartQuantity = $('#header-cart-quantity');
        var $headerCartDropdown = $('#header-cart-dropdown');
        var $headerCartQuantity = $headerCartWrapper.find('#header-cart-quantity');
        var newCartDropdown =
            $(`
        <div class="navbar-cart-product-wrapper">
            <!-- cart item-->
            ${cart.map((cartItem, index) => {
                var item = cartItem.item;
                return `
                    <div class="navbar-cart-product">
                        <div class="d-flex align-items-center">
                            <a href="/mike/collection/shop/product-detail/${item.id.productId}"><img
                                class="img-fluid navbar-cart-product-image"
                                src="/images/product/${item.avatar}"
                                alt="...">
                            </a>
                            <div class="w-100">
                                <a class="navbar-cart-product-close" href="#">
                                    <i class="fa fa-times"> </i>
                                </a>
                                <div class="ps-3">
                                    <a class="navbar-cart-product-link"
                                        href="/mike/collection/shop/product-detail/${item.id.productId}">${item.name}
                                    </a>
                                    <small class="d-block text-muted">Color: ${item.itemDetail.color.name} </small>
                                    <small class="d-block text-muted">Size: ${item.itemDetail.size.name} </small>
                                    <small class="d-block text-muted">Quantity: ${cartItem.quantity} </small>
                                    <small class="d-block text-muted">${cartItem.price.toFixed(2)} </small>
                                </div>
                            </div>
                        </div>
                    </div>
                    `;
            }).join('')}
        </div>
        <!-- total price-->
        <div class="navbar-cart-total">
            <span class="text-uppercase text-muted">Total</span>
            <strong class="text-uppercase">${totalAmount.toFixed(2)}</strong>
        </div>
        <!-- buttons-->
        <div class="d-flex justify-content-between">
            <a class="btn btn-link text-dark me-3" href="/mike/cart/detail">View Cart
                <i class="fa-arrow-right fa"></i>
            </a>
            <a class="btn btn-outline-dark" href="/mike/cart/checkout-address">Checkout</a>
        </div>
        `);
        $headerCartDropdown.empty();
        $headerCartDropdown.append(newCartDropdown);
        $headerCartQuantity.text(totalQuantity);
        $('#header-cart-quantity-mobile').text(totalQuantity);
    }
});
