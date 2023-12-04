$(document).ready(function () {
    var url = "/mike/api/product/product-filter?filter=(;sbi=DESC;pag=12.1;)";
    url = url.replace(/\s+/g, "");
    getProductData(url);

    let productUserItems = $('.product-user-item');
    let productCategoryItems = $('.product-category-item');
    let productPriceLower = $('.product-price-lower');
    let productPriceUpper = $('.product-price-upper');
    let productSizeItems = $('.product-size-item');
    let productColorItems = $('.product-color-item');
    let productSortItem = $('.product-sort-item');
    let paginationOptions = $('.pagination-option');

    productUserItems.on("click", function () {
        productUserItems.not($(this)).removeClass('active');
        if ($(this).hasClass('active')) {
            $(this).removeClass('active');
            $(this).blur();
        }
        else {
            $(this).addClass('active');
        }
        updateAndCallAPI();
    });

    productCategoryItems.on("click", function () {
        productCategoryItems.not($(this)).removeClass('active');
        if ($(this).hasClass('active')) {
            $(this).removeClass('active');
            $(this).blur();
        }
        else {
            $(this).addClass('active');
        }
        updateAndCallAPI();
    });

    productPriceLower.on("change", function () {
        updateAndCallAPI();
    });

    productPriceUpper.on("change", function () {
        updateAndCallAPI();
    });

    productSizeItems.on("change", function () {
        updateAndCallAPI();
    });

    productColorItems.on("change", function () {
        updateAndCallAPI();
    });

    productSortItem.on("change", function () {
        updateAndCallAPI();
    });

    paginationOptions.on("click", function () {
        paginationOptions.not($(this)).removeClass('active');
        $(this).addClass('active');
        $("#pagination").find(".page-item").removeClass('active');
        updateAndCallAPI();
    });

    $("#pagination").on("click", ".page-item:not(.active)", function () {
        var $pagination = $("#pagination");
        var $pageItems = $pagination.find('.page-item');
        var $clickedItem = $(this);

        if ($clickedItem.hasClass("prev-item")) {
            var $activeItem = $pagination.find('.page-item.active');
            var index = $pageItems.index($activeItem);

            if (index !== -1 && index > 1) {
                $pageItems.removeClass('active');
                $pageItems.eq(index - 1).addClass('active');
                updateAndCallAPI();
            }
        } else if ($clickedItem.hasClass("next-item")) {
            var $activeItem = $pagination.find('.page-item.active');
            var index = $pageItems.index($activeItem);

            if (index !== -1 && index < $pageItems.length - 2) {
                $pageItems.removeClass('active');
                $pageItems.eq(index + 1).addClass('active');
                updateAndCallAPI();
            }
        } else {
            $pageItems.not($clickedItem).removeClass('active');
            $clickedItem.addClass('active');
            updateAndCallAPI();
        }
    });

    function updateAndCallAPI(addPagination = true) {
        var filter = "(;";

        // Retrieve the selected options
        var productUserId = null;
        productUserItems.each(function () {
            if ($(this).hasClass("active")) {
                productUserId = $(this).attr('data-product-user-item-id');
            }
        });

        var productCategoryId = null;
        productCategoryItems.each(function () {
            if ($(this).hasClass("active")) {
                productCategoryId = $(this).attr('data-product-category-item-id');
            }
        });

        var productPriceLowerValue = productPriceLower.val();
        var productPriceUpperValue = productPriceUpper.val();

        var sizeList = productSizeItems
            .filter(":checked")
            .map(function () {
                return $(this).val();
            })
            .get()
            .join(".");

        var colorList = productColorItems
            .filter(":checked")
            .map(function () {
                return $(this).val();
            })
            .get()
            .join(".");

        var productSortItemValue = productSortItem.val();

        var paginationOptionValue = null;
        paginationOptions.each(function () {
            if ($(this).hasClass("active")) {
                paginationOptionValue = parseInt($(this).text().replace(" ", ""));
            }
        });

        var pageLinkValue = null;
        $("#pagination").find(".page-item").each(function () {
            if ($(this).hasClass("active")) {
                pageLinkValue = parseInt($(this).text().replace(" ", ""));
            }
        });

        // Build the filter parameter based on the selected options
        if (productUserId) {
            filter += "oc=" + productUserId + ";";
        }
        if (productCategoryId) {
            filter += "pc=" + productCategoryId + ";";
        }
        if (sizeList) {
            filter += "s=" + sizeList + ";";
        }
        if (colorList) {
            filter += "c=" + colorList + ";";
        }
        if (productPriceLowerValue && productPriceUpperValue) {
            filter += "mip=" + productPriceLowerValue + ";";
            filter += "map=" + productPriceUpperValue + ";";
        }

        if (paginationOptionValue) {
            if ($.isNumeric(paginationOptionValue) && parseInt(paginationOptionValue) === parseFloat(paginationOptionValue)) {
                filter += "pag=" + paginationOptionValue + ".1;";
            } else {
                filter += "pag=;";
            }
        }

        if (pageLinkValue) {
            if (filter.includes("pag=")) {
                var startIndex = filter.indexOf("pag=");
                var endIndex = filter.indexOf(";", startIndex);
                var pagValue = filter.substring(startIndex, endIndex);

                if (pagValue.length > 5) {
                    var newPagValue = pagValue.replace(/(\.\d+)$/, '.' + pageLinkValue);
                    filter = filter.replace(pagValue, newPagValue);
                }
            }
        }

        // Add sorting options
        if (productSortItemValue) {
            if (productSortItemValue === "price_ASC") {
                filter += "sbp=ASC;";
            } else if (productSortItemValue === "price_DESC") {
                filter += "sbp=DESC;";
            } else if (productSortItemValue === "name_ASC") {
                filter += "sbn=ASC;";
            } else if (productSortItemValue === "name_DESC") {
                filter += "sbn=DESC;";
            } else if (productSortItemValue === "id_ASC") {
                filter += "sbi=ASC;";
            } else if (productSortItemValue === "id_DESC") {
                filter += "sbi=DESC;";
            }
        }

        // Complete the filter parameter
        filter += ")";

        // Update the URL with the new filter parameter
        url = "/mike/api/product/product-filter?filter=" + encodeURIComponent(filter);
        url = url.replace(/\s+/g, "");
        // Make the AJAX request with the updated URL
        getProductData(url, addPagination = true);
    }

    function getProductData(url, addPagination = true) {
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function (response) {
                // Get the wrapper element
                var $wrapper = $('#wrapper-list-product');

                // Clear existing product elements
                $wrapper.empty();

                // Iterate over the products and add them to the wrapper element
                if (response.listProduct.length > 0) {
                    for (var i = 0; i < response.listProduct.length; i++) {
                        var res = response[i];
                        var list = response.listProduct[i];
                        // Create a new product element
                        var $productElement =
                            $(`
                <div class="col-xl-4 col-sm-6 wrapper-product" data-product-id="${list.product.id}">
                  <div class="product">
                    <div class="product-image">
                      ${list.product.bestSellerStatus === 'ACTIVE' ? '<div class="ribbon ribbon-success">Best seller</div>' : ''}
                      ${list.product.hotStatus === 'ACTIVE' ? '<div class="ribbon ribbon-warning">Hot</div>' : ''}
                      <img class="img-fluid" src="/images/product/${list.product.avatar}" alt="product" />
                      <div class="product-hover-overlay">
                        <a class="product-hover-overlay-link" href="/mike/collection/shop/product-detail/${list.product.id}"></a>
                        <div class="product-hover-overlay-buttons">
                          <a class="btn btn-outline-dark btn-product-left d-none d-sm-inline-block" href="#"><i class="fa fa-shopping-cart"></i></a>
                          <a class="btn btn-dark btn-buy" href="/mike/collection/shop/product-detail/${list.product.id}"><i class="fa-search fa"></i><span class="btn-buy-label ms-2">View</span></a>
                          <a class="btn btn-outline-dark btn-product-right d-none d-sm-inline-block" href="#" data-bs-toggle="modal" data-bs-target="#exampleModal"><i class="fa fa-expand-arrows-alt"></i></a>
                        </div>
                      </div>
                    </div>
                    <div class="py-2">
                      <p class="text-muted text-sm mb-1">${list.product.productCategory.name}</p>
                      <h3 class="h6 text-uppercase mb-1"><a class="text-dark" href="/mike/collection/shop/product-detail/${list.product.id}">${list.product.name}</a></h3>
                      <span class="text-muted">
                          ${list.minPrice === list.maxPrice ? list.maxPrice + 'đ' : list.minPrice + 'đ - ' + list.maxPrice + 'đ'}
                      </span>
                    </div>
                  </div>
                </div>
                `);
                        // Append the product element to the wrapper
                        $wrapper.append($productElement);
                    }
                } else {
                    var $elementChild =
                        $(` <div class="d-flex justify-content-center align-items-center px-3" style="min-height: 300px;">
                          <h4 class="text-danger text-center">The products you requested do not exist yet...</h4>
                      </div>
                  `);
                    $wrapper.append($elementChild);
                }
                // Pagination
                if (addPagination) {
                    var $pagination = $('#pagination');
                    $pagination.empty();

                    if (response.totalPage > 1) {
                        var $prevItem = $('<li class="page-item prev-item disabled"><a class="page-link" href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">Prev</span><span class="sr-only">Previous</span></a></li>');
                        var $nextItem = $('<li class="page-item next-item"><a class="page-link" href="javascript:void(0)" aria-label="Next"><span aria-hidden="true">Next</span><span class="sr-only">Next</span></a></li>');

                        if (response.currentPage > 1) {
                            $prevItem.removeClass('disabled');
                            $prevItem.on('click', function () {
                                // Xử lý khi nút "Prev" được nhấp
                                // ...
                            });
                        }

                        if (response.currentPage < response.totalPage) {
                            $nextItem.on('click', function () {
                                // Xử lý khi nút "Next" được nhấp
                                // ...
                            });
                        } else {
                            $nextItem.addClass('disabled');
                        }

                        $pagination.append($prevItem);

                        // Giới hạn số trang hiển thị
                        var startPage = Math.max(1, response.currentPage - 1);
                        var endPage = Math.min(response.totalPage, startPage + 2);

                        for (var i = startPage; i <= endPage; i++) {
                            var $pageItem = $('<li class="page-item"><a class="page-link" href="javascript:void(0)">' + i + '</a></li>');

                            if (i === response.currentPage) {
                                $pageItem.addClass('active');
                            }

                            $pagination.append($pageItem);
                        }

                        $pagination.append($nextItem);
                    }
                }
                //
                var showTitleNumber = $('#showTitleNumber');
                $('.pagination-option').each(function () {
                    if ($(this).hasClass("active")) {
                        var pageItem = " 1-" + $(this).text().replace(" ", "") + " ";
                        showTitleNumber.find('strong').eq(0).text(pageItem);
                        showTitleNumber.find('strong').eq(1).text(response.totalProduct + " ");
                    }
                });
            },
            error: function (xhr, status, error) {
                // Handle the error if the request fails
                console.log('Error:', error);
            }
        });
    }

    $('.clear-filter').on('click', function () {
        $('.product-user-item').removeClass('active');
        $('.product-category-item').removeClass('active');
        $('.product-size-item:checked').prop('checked', false);
        $('.product-color-item:checked').siblings().removeClass('active');
        $('.product-color-item:checked').prop('checked', false);
        $('.product-sort-item').val('');
        $('.pagination-option').removeClass('active');
        $('.pagination-option').eq(0).addClass('active');

        var existingSlider = $('#slider-snap')[0];
        if (existingSlider) {
            existingSlider.noUiSlider.destroy();
        }

        var snapSlider = $('#slider-snap')[0];
        noUiSlider.create(snapSlider, {
            start: [0.0, 2500000.0],
            snap: false,
            connect: true,
            step: 10000.0,
            range: {
                'min': 0.0,
                'max': 2500000.0
            }
        });

        var snapValues = [
            $('#slider-snap-value-lower')[0],
            $('#slider-snap-value-upper')[0]
        ];
        var inputValues = [
            $('#slider-snap-input-lower')[0],
            $('#slider-snap-input-upper')[0]
        ];

        snapSlider.noUiSlider.on('update', function (values, handle) {
            $(snapValues[handle]).html(values[handle]);
        });

        snapSlider.noUiSlider.on('change', function (values) {
            $.each(inputValues, function (index, input) {
                input.value = values[index];
                $(input).change();
            });
        });

        updateAndCallAPI();
    });


});