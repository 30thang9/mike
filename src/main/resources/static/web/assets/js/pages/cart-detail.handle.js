$(document).ready(function() {
    $('.btn-items-increase').on('click', function(){
        updateItem($(this));
    })

    $('.btn-items-decrease').on('click', function(){
        var quantityInput = $(this).closest('div.cart-item').find('.cart-item-quantity').eq(0);
        var currentQuantity = parseInt(quantityInput.val());
        if (currentQuantity === 0) {
            quantityInput.val(1);
            return;
        }
        updateItem($(this));
    })

    $('.cart-item-quantity').on('change', function(){
        var quantityInput = $(this);
        var currentQuantity = parseInt(quantityInput.val());
        if (currentQuantity === 0) {
            quantityInput.val('');
            return;
        }
        updateItem($(this));
    })

    $('.cart-item-quantity').on('input', function() {
        var inputValue = $(this).val();
        var regex = /^[1-9][0-9]*$/;
        if (!regex.test(inputValue)) {
            $(this).val(inputValue.replace(/[^0-9]/g, ''));
        }
    });

    $('.remove-cart-item').on('click', function(){
        alertConfirmDelete($(this));
    })
    
    function updateItem($btn) {
        var productId = $btn.closest('div.cart-item').find('.product-name').eq(0).data('product-id');
        var sizeId = $btn.closest('div.cart-item').find('.size-name').eq(0).data('size-id');
        var colorId = $btn.closest('div.cart-item').find('.color-name').eq(0).data('color-id');
        var quantity = $btn.closest('div.cart-item').find('.cart-item-quantity').eq(0).val();
        var price = $btn.closest('div.cart-item').find('.cart-item-price').eq(0).text();

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

        var url = "/mike/api/cart/update";
        $.ajax({
            url: url,
            type: 'PUT',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(data) {
                location.reload();
            },
            error: function(xhr, status, error) {

            }
        });
    }

    function deleteItem($btn){
        var productId = $btn.closest('div.cart-item').find('.product-name').eq(0).data('product-id');
        var sizeId = $btn.closest('div.cart-item').find('.size-name').eq(0).data('size-id');
        var colorId = $btn.closest('div.cart-item').find('.color-name').eq(0).data('color-id');

        var formData =
        {
            productId: productId,
            sizeId: sizeId,
            colorId: colorId
        }

        var url = "/mike/api/cart/delete";
        $.ajax({
            url: url,
            type: 'DELETE',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(data) {
                showAlert("Delete item successfully!",true);
            },
            error: function(xhr, status, error) {
                showAlert("Delete item failed!",false);
            }
        });
    }

    function alertConfirmDelete($btn){
        Swal.fire({
            title: 'Confirmation',
            text: 'Are you want to delete this item?',
            icon: 'question',
            showCancelButton: true,
            cancelButtonText: 'Cancel',
            confirmButtonText: 'Ok',
            customClass: {
                confirmButton: 'btn btn-primary btn-confirm-remove',
                cancelButton: 'btn btn-secondary'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                deleteItem($btn);
            }
        });
    }

    function showAlert(message, isSuccess) {
        Swal.fire({
            title: message,
            icon: isSuccess ? 'success' : 'error',
            timer: 1200,
            timerProgressBar: true,
            showConfirmButton: false,
            customClass: {
                timerProgressBar: isSuccess ? 'bg-success' : 'bg-danger'
            },
            willClose: () => {
                if (isSuccess) {
                    location.reload();
                }
            }
        });
    }

    $("#btn-checkout").on('click',function () {
        $.ajax({
            url:'/mike/api/cart/invalid-cart',
            type:'GET',
            dataType: "JSON",
            success: function(data) {
                window.location.href = "/mike/cart/checkout-address";
            },
            error: function (jqXHR, textStatus, errorThrown){
                if (jqXHR.status === 422) {
                    window.location.href = "/mike/cart/invalid-item";
                } else {

                }
            }
        })
    })
})