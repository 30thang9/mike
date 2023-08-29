$(document).ready(function () {
    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 0,
        nav: false,
        dots: true,
        smartSpeed: 450,
        responsive: {
            0: {
                items: 1
            }
        }
    });
    $('.owl-carousel').css('position', 'relative');
    $('.owl-dots').css('position', 'absolute');
    $('.owl-dots').css('bottom', '0px');
    $('.owl-dots').css('left', '50%');
    $('.owl-dots').css('transform', 'translate(-50%, -50%)');
});

$(document).ready(function () {
    var myForm = document.getElementById('addListImagesForm');

    myForm.addEventListener('submit', function (event) {
        event.preventDefault();

        var dropzoneElement = myForm.querySelector('.dropzone.dropzone-multiple.dropzone-add-list_product_image');
        var files = dropzoneElement.dropzone.files;
        var productId = $(dropzoneElement).data('product-id');
        if (files && files.length > 0 && myForm.checkValidity()) {
            var formData = new FormData();
            formData.append('productId', productId);

            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                formData.append('listImages', file, file.name);
            }

            var url = myForm.getAttribute('action');

            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    showModal("Add success!", true);
                },
                error: function (xhr, status, error) {
                    var errorMessage = "Add error: " + error;
                    showModal(errorMessage, false);
                }
            });
        }
    });

    function showModal(message, isSuccess) {
        var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
        var modalBody = $("<div class='modal-body'></div>").text(message);
        var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
        var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
        var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
        var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
        var modal = $("<div class='modal fade'></div>").append(modalDialog);

        closeButton.on("click", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.modal("hide");
        });

        modal.on("hidden.bs.modal", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.remove();
        });

        $("body").append(modal);
        modal.modal("show");
    }
});

$(document).ready(function () {
    const myForm = document.getElementById('editListImagesForm');
    const $messageValidEditImage = $('#message-valid-editImage');

    myForm.addEventListener('submit', function (event) {
        event.preventDefault();

        var dropzoneElement = myForm.querySelector('.dropzone.dropzone-multiple.dropzone-edit-list_product_image');
        var files = dropzoneElement.dropzone.files;
        var sizeImage = $(dropzoneElement).data('size-image');

        if (sizeImage <= 0) {
            var message = `You don't have any photos yet, please switch to add new photo mode.
                            <br>
                            <span class="text-secondary cursor-pointer" 
                                data-bs-toggle="modal" data-bs-target="#addListImages">Click to add!
                            </span>
                          `;
            showMessage(message, true);
            return;
        }
        if (files && files.length > 0) {
            if (files.length != sizeImage) {
                var message = "Make sure the number of photos is " + sizeImage;
                showMessage(message, true);
                return;
            }
        }
        if (myForm.checkValidity()) {
            var formData = new FormData();
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                if (file.status === 'success') {
                    formData.append('listImages', file, file.name);
                }
            }
            var url = myForm.getAttribute('action');

            $.ajax({
                url: url,
                type: 'PUT',
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    showModal("Edit success!", true);
                },
                error: function (xhr, status, error) {
                    var errorMessage = "Edit error: " + error;
                    showModal(errorMessage, false);
                }
            });
        }
    });

    function showMessage(message, isError) {
        const alertClass = isError ? 'danger' : 'success';
        const alertContent = `
            <div class="alert alert-${alertClass} alert-dismissible d-flex position-relative" role="alert">
                <span class="badge badge-center rounded-pill bg-${alertClass} border-label-${alertClass} p-3 me-2"><i class="bx bx-store fs-6"></i></span>
                <div class="d-flex flex-column ps-1">
                    <h6 class="alert-heading d-flex align-items-center mb-1">${isError ? 'Error!!' : 'Success'}</h6>
                    <span>${message}</span>
                </div>
                <button type="button" id ="btn-close-msgErrProductDetail"  class="btn-close position-absolute" data-bs-dismiss="alert" aria-label="Close" style="top:10px;right:5px;"></button>
            </div>
        `;

        $messageValidEditImage.html(alertContent);

        // Adjust button style
        const $btnCloseMsgErr = $messageValidEditImage.find('button');
        const parentBtnStyle = $btnCloseMsgErr.closest('div').css(['backgroundColor', 'borderColor']);
        $btnCloseMsgErr.css(parentBtnStyle);
    }

    function showModal(message, isSuccess) {
        var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
        var modalBody = $("<div class='modal-body'></div>").text(message);
        var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
        var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
        var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
        var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
        var modal = $("<div class='modal fade'></div>").append(modalDialog);

        closeButton.on("click", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.modal("hide");
        });

        modal.on("hidden.bs.modal", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.remove();
        });

        $("body").append(modal);
        modal.modal("show");
    }
});

$(document).ready(function () {
    const myForm = document.getElementById('deleteListImagesForm');
    const $messageValidEditImage = $('#message-valid-deleteImage');

    myForm.addEventListener('submit', function (event) {
        event.preventDefault();

        var dropzoneElement = myForm.querySelector('#dropzone-list-delete-temp');
        var files = dropzoneElement.dropzone.files;

        if (files && files.length <= 0) {
            var dropzoneElementDel = myForm.querySelector('#dropzone-list-delete');
            var lengthFile = dropzoneElementDel.dropzone.files.length;
            var message = "You must delete one or more images from the dropzone";
            if (lengthFile === 0) {
                message = `You don't have any photos yet, please switch to add new photo mode.
                            <br>
                            <span class="text-secondary cursor-pointer" 
                                data-bs-toggle="modal" data-bs-target="#addListImages">Click to add!
                            </span>
                          `;
            }
            showMessage(message, true);
            return;
        }

        if (myForm.checkValidity()) {
            var formData = [];
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                if (file.status === 'success') {
                    formData.push(file.name);
                }
            }

            var dataOb = {
                listImage: formData
            }

            var jsonData = JSON.stringify(dataOb);

            console.log(jsonData);

            var url = myForm.getAttribute('action');

            $.ajax({
                url: url,
                type: 'DELETE',
                contentType: 'application/json',
                data: jsonData,
                success: function (response) {
                    showModal("Delete success!", true);
                },
                error: function (xhr, status, error) {
                    var errorMessage = "Delete error: " + error;
                    showModal(errorMessage, false);
                }
            });
        }
    });

    function showMessage(message, isError) {
        const alertClass = isError ? 'danger' : 'success';
        const alertContent = `
            <div class="alert alert-${alertClass} alert-dismissible d-flex position-relative" role="alert">
                <span class="badge badge-center rounded-pill bg-${alertClass} border-label-${alertClass} p-3 me-2"><i class="bx bx-store fs-6"></i></span>
                <div class="d-flex flex-column ps-1">
                    <h6 class="alert-heading d-flex align-items-center mb-1">${isError ? 'Error!!' : 'Success'}</h6>
                    <span>${message}</span>
                </div>
                <button type="button" id ="btn-close-msgErrProductDetail"  class="btn-close position-absolute" data-bs-dismiss="alert" aria-label="Close" style="top:10px;right:5px;"></button>
            </div>
        `;

        $messageValidEditImage.html(alertContent);

        // Adjust button style
        const $btnCloseMsgErr = $messageValidEditImage.find('button');
        const parentBtnStyle = $btnCloseMsgErr.closest('div').css(['backgroundColor', 'borderColor']);
        $btnCloseMsgErr.css(parentBtnStyle);
    }

    function showModal(message, isSuccess) {
        var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
        var modalBody = $("<div class='modal-body'></div>").text(message);
        var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
        var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
        var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
        var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
        var modal = $("<div class='modal fade'></div>").append(modalDialog);

        closeButton.on("click", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.modal("hide");
        });

        modal.on("hidden.bs.modal", function () {
            if (isSuccess) {
                location.reload();
            }
            modal.remove();
        });

        $("body").append(modal);
        modal.modal("show");
    }
});

$(document).ready(function () {
    function initializeDropzone(dropzoneElement, url, resetButton = null, deleteDropzoneElementTemp = null) {
        var dropzone = Dropzone.forElement(dropzoneElement);
        var isDelAll = false;
        function addFileToDropzone(fileName) {
            var fileUrl = "/images/product/" + fileName;
            fetch(fileUrl)
                .then(response => response.blob())
                .then(blob => {
                    var file = new File([blob], fileName, { type: blob.type });
                    dropzone.addFile(file);
                })
                .catch(error => {
                    console.log('Error fetching file:', error);
                });
        }

        function reloadAndRefreshDropzone() {
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                success: function (response) {
                    var productImage = response.productImage;
                    dropzone.removeAllFiles();

                    for (var i = 0; i < productImage.length; i++) {
                        var fileName = productImage[i];
                        addFileToDropzone(fileName);
                    }

                    dropzone.options.maxFiles = productImage.length;
                    $(dropzoneElement).data('size-image', productImage.length);

                    if (deleteDropzoneElementTemp) {
                        resetDelTemp(deleteDropzoneElementTemp);
                    }
                },
                error: function (xhr, status, error) {
                    console.log(error);
                }
            });

        }

        reloadAndRefreshDropzone();

        function resetDelTemp(drop) {
            var dropzoneDeleteTemp = Dropzone.forElement(drop);
            dropzoneDeleteTemp.removeAllFiles();
        }

        // Attach click event for reloading and refreshing Dropzone
        if (resetButton) {
            $(resetButton).on('click', function () {
                reloadAndRefreshDropzone();
            });
        }
    }

    function moveFileBetweenDropzones(targetDropzone, file) {
        var targetDropzoneInstance = Dropzone.forElement(targetDropzone);
        targetDropzoneInstance.addFile(file);
    }

    // Initialize Dropzone for the first element
    var editDropzoneElement = $(".dropzone.dropzone-edit-list_product_image")[0];
    var editResetButton = $("#reset-edit-image"); // Adjust ID as needed
    var editProductId = $(editDropzoneElement).data('product-id');
    var editUrl = "/mike/api/product/product-single/" + editProductId;
    initializeDropzone(editDropzoneElement, editUrl, editResetButton);

    var dropzoneEdit = Dropzone.forElement(editDropzoneElement);
    dropzoneEdit.on("addedfile", function (file) {
        if (this.files.length > this.options.maxFiles) {
            var removedFile = this.files.pop();
            this.removeFile(removedFile);
        }
    });

    // Initialize Dropzone for the second element (without reset button)
    var deleteDropzoneElement = $("#dropzone-list-delete")[0];
    var deleteResetButton = $("#reset-delete-image"); // Adjust ID as needed
    var deleteProductId = $(deleteDropzoneElement).data('product-id');
    var deleteUrl = "/mike/api/product/product-single/" + deleteProductId;

    var deleteDropzoneElementTemp = $("#dropzone-list-delete-temp")[0];

    initializeDropzone(deleteDropzoneElement, deleteUrl, deleteResetButton, deleteDropzoneElementTemp);

    var dropzoneDelete = Dropzone.forElement(deleteDropzoneElement);
    //    var dropzoneDeleteTemp = Dropzone.forElement(deleteDropzoneElementTemp);
    dropzoneDelete.on("addedfile", function (file) {
        if (this.files.length > this.options.maxFiles) {
            var removedFile = this.files.pop();
            this.removeFile(removedFile);
        }
    });
    dropzoneDelete.on('removedfile', function (file) {
        var maxFile = this.options.maxFiles;
        if (this.files.length < maxFile) {
            this.options.maxFiles = maxFile - 1;
            moveFileBetweenDropzones(deleteDropzoneElementTemp, file);
        }
    });
});



$(document).ready(function () {
    const $inputAddAtr = $('#addProductDetailForm .form-check-input.add-attribute-input');
    const $inputCheckAll = $('#addProductDetailForm .input-add-check-all');
    const $messageValidPrice = $('#message-add-valid-price');
    const $form = $('#addProductDetailForm');

    function showMessage(message, isError) {
        const alertClass = isError ? 'danger' : 'success';
        const alertContent = `
            <div class="alert alert-${alertClass} alert-dismissible d-flex position-relative" role="alert">
                <span class="badge badge-center rounded-pill bg-${alertClass} border-label-${alertClass} p-3 me-2"><i class="bx bx-store fs-6"></i></span>
                <div class="d-flex flex-column ps-1">
                    <h6 class="alert-heading d-flex align-items-center mb-1">${isError ? 'Error!!' : 'Success'}</h6>
                    <span>${message}</span>
                </div>
                <button type="button" id ="btn-close-msgErrProductDetail"  class="btn-close position-absolute" data-bs-dismiss="alert" aria-label="Close" style="top:10px;right:5px;"></button>
            </div>
        `;

        $messageValidPrice.html(alertContent);

        // Adjust button style
        const $btnCloseMsgErr = $messageValidPrice.find('button');
        const parentBtnStyle = $btnCloseMsgErr.closest('div').css(['backgroundColor', 'borderColor']);
        $btnCloseMsgErr.css(parentBtnStyle);
    }

    function validatePrice($inputPrice, icon) {
        const isVal = $inputPrice.toArray().every(input => $(input).val());
        icon.toggleClass('text-success', isVal).toggleClass('text-danger', !isVal);
        return isVal;
    }

    function updateIconState($inputPrice, icon) {
        $inputPrice.on('change', function () {
            const $parent = $(this).closest('.dropdown');
            const isVal = validatePrice($parent.find('input'), icon);
            icon.toggleClass('text-success', isVal).toggleClass('text-danger', !isVal);
        });
    }

    function handleCheckAll(colorId, isChecked, icon) {
        const $wrapperAddAtr = $('#addProductDetailForm .wrapper-add-attribute');
        $wrapperAddAtr.find(`input[data-color-id="${colorId}"].add-attribute-input`).prop('checked', isChecked).trigger('change');
        if (!isChecked) {
            $('[id^="bs-validation-add-iPrice_"][id$="-' + colorId + '"]').val('');
            $('[id^="bs-validation-add-ePrice_"][id$="-' + colorId + '"]').val('');
            icon.removeClass('text-danger text-success');
        }
    }

    $inputAddAtr.on('change', function () {
        const $this = $(this);
        const wrapper = $this.closest('.wrapper-add-attribute');
        const dropdown = wrapper.find('.dropdown').eq(0);
        const icon = dropdown.find('button.icon-toggle i');
        const $inputPrice = dropdown.find('input');
        validatePrice($inputPrice, icon);

        if (!$this.is(':checked')) {
            icon.removeClass('text-success text-danger');
        }
    });

    $('#addProductDetailForm .dropdown input').each(function () {
        const $this = $(this);
        const $parent = $this.closest('.dropdown');
        updateIconState($parent.find('input'), $parent.find('button.icon-toggle i'));
    });



    $inputCheckAll.on('change', function () {
        const colorId = $(this).val().trim();
        const isChecked = $(this).is(':checked');
        const dropdown = $(this).closest('.wrapper-add-check-all').find('.dropdown').eq(0);
        if ($(this).is(':checked')) {
            dropdown.find('button').eq(0).click();
        }
        handleCheckAll(colorId, isChecked, dropdown.find('button.icon-toggle i'));
    });

    $('[id^="btn_ofAdd_applyIPrice_all_"]').on('click', function () {
        const id = $(this).attr('id');
        const colorId = id.substring(id.lastIndexOf('_') + 1);
        const dropMenu = $(this).closest('.dropdown-menu');
        const iPriceVal = dropMenu.find('[id^="bs-validation-add-iPrice_all_"]').val().trim();
        if (iPriceVal) {
            $('[id^="bs-validation-add-iPrice_"][id$="-' + colorId + '"]').val(iPriceVal).trigger('change');
        }
    });

    $('[id^="btn_ofAdd_applyEPrice_all_"]').on('click', function () {
        const id = $(this).attr('id');
        const colorId = id.substring(id.lastIndexOf('_') + 1);
        const dropMenu = $(this).closest('.dropdown-menu');
        const ePriceVal = dropMenu.find('[id^="bs-validation-add-ePrice_all_"]').val().trim();
        if (ePriceVal) {
            $('[id^="bs-validation-add-ePrice_"][id$="-' + colorId + '"]').val(ePriceVal).trigger('change');
        }
    });

    $form.on('submit', function (event) {
        event.preventDefault();
        var isAnyChecked = false;
        var isValidPrice = true;
        var href = window.location.href;
        var productId = href.substring(href.lastIndexOf("/") + 1);
        var formData = [];

        function showModal(message, isSuccess) {
            var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
            var modalBody = $("<div class='modal-body'></div>").text(message);
            var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
            var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
            var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
            var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
            var modal = $("<div class='modal fade'></div>").append(modalDialog);

            closeButton.on("click", function () {
                if (isSuccess) {
                    location.reload();
                }
                modal.modal("hide");
            });

            modal.on("hidden.bs.modal", function () {
                if (isSuccess) {
                    location.reload();
                }
                modal.remove();
            });

            $("body").append(modal);
            modal.modal("show");
        }

        $inputAddAtr.each(function () {
            const $this = $(this);
            if ($this.is(':checked')) {
                isAnyChecked = true;
                const wrapper = $this.closest('.wrapper-add-attribute');
                const dropdown = wrapper.find('.dropdown').eq(0);
                const icon = dropdown.find('button.icon-toggle i');
                const $inputPrice = dropdown.find('input');
                const isVal = validatePrice($inputPrice, icon);

                if (!isVal) {
                    isValidPrice = false;
                    return false;
                }

                var value = $this.val().trim();
                var valSplit = value.split('-');
                var sizeId;
                var colorId;
                var importPrice;
                var exportPrice;

                if ($inputPrice.length === 2) {
                    importPrice = $inputPrice.eq(0).val().trim();
                    exportPrice = $inputPrice.eq(1).val().trim();
                }

                if (valSplit.length === 2) {
                    sizeId = valSplit[0];
                    colorId = valSplit[1];
                }

                var productDetail = {
                    "productId": productId,
                    "colorId": colorId,
                    "sizeId": sizeId,
                    "importPrice": importPrice,
                    "exportPrice": exportPrice
                };

                formData.push(productDetail);

            }
        });

        if (!isAnyChecked) {
            showMessage('Please choose any element.', true);
            return false;
        }

        if (!isValidPrice) {
            showMessage('Please edit price of danger edit icons.', true);
            return false;
        }

        $.ajax({
            url: '/mike/api/product/product-detail/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                showModal("Add success!", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
                showModal("Add error!", false);
            }
        });

    });
});

$(document).ready(function () {
    const $inputEditAtr = $('#editProductDetailForm .form-check-input.edit-attribute-input');
    const $messageValidPrice = $('#message-delete-valid-price');
    const $form = $('#editProductDetailForm');

    function showMessage(message, isError) {
        const alertClass = isError ? 'danger' : 'success';
        const alertContent = `
            <div class="alert alert-${alertClass} alert-dismissible d-flex position-relative" role="alert">
                <span class="badge badge-center rounded-pill bg-${alertClass} border-label-${alertClass} p-3 me-2"><i class="bx bx-store fs-6"></i></span>
                <div class="d-flex flex-column ps-1">
                    <h6 class="alert-heading d-flex align-items-center mb-1">${isError ? 'Error!!' : 'Success'}</h6>
                    <span>${message}</span>
                </div>
                <button type="button" id ="btn-close-msgErrProductDetail"  class="btn-close position-absolute" data-bs-dismiss="alert" aria-label="Close" style="top:10px;right:5px;"></button>
            </div>
        `;

        $messageValidPrice.html(alertContent);

        // Adjust button style
        const $btnCloseMsgErr = $messageValidPrice.find('button');
        const parentBtnStyle = $btnCloseMsgErr.closest('div').css(['backgroundColor', 'borderColor']);
        $btnCloseMsgErr.css(parentBtnStyle);
    }

    function validatePrice($inputPrice, icon) {
        const isVal = $inputPrice.toArray().every(input => $(input).val());
        icon.toggleClass('text-success', isVal).toggleClass('text-danger', !isVal);
        return isVal;
    }

    function updateIconState($inputPrice, icon) {
        $inputPrice.on('change', function () {
            const $parent = $(this).closest('.dropdown');
            const isVal = validatePrice($parent.find('input'), icon);
            icon.toggleClass('text-success', isVal).toggleClass('text-danger', !isVal);
        });
    }

    $('#editProductDetailForm .dropdown input').each(function () {
        const $this = $(this);
        const $parent = $this.closest('.dropdown');
        updateIconState($parent.find('input'), $parent.find('button.icon-toggle i'));
    });

    $('[id^="btn_ofEdit_applyIPrice_all_"]').on('click', function () {
        const id = $(this).attr('id');
        const colorId = id.substring(id.lastIndexOf('_') + 1);
        const dropMenu = $(this).closest('.dropdown-menu');
        const iPriceVal = dropMenu.find('[id^="bs-validation-edit-iPrice_all_"]').val().trim();
        if (iPriceVal) {
            $('[id^="bs-validation-edit-iPrice_"][id$="-' + colorId + '"]').val(iPriceVal).trigger('change');
        }
    });

    $('[id^="btn_ofEdit_applyEPrice_all_"]').on('click', function () {
        const id = $(this).attr('id');
        const colorId = id.substring(id.lastIndexOf('_') + 1);
        const dropMenu = $(this).closest('.dropdown-menu');
        const ePriceVal = dropMenu.find('[id^="bs-validation-edit-ePrice_all_"]').val().trim();
        if (ePriceVal) {
            $('[id^="bs-validation-edit-ePrice_"][id$="-' + colorId + '"]').val(ePriceVal).trigger('change');
        }
    });

    $form.on('submit', function (event) {
        event.preventDefault();
        var isAnyChecked = false;
        var isValidPrice = true;
        var href = window.location.href;
        var productId = href.substring(href.lastIndexOf("/") + 1);
        var formData = [];

        function showModal(message, isSuccess) {
            var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
            var modalBody = $("<div class='modal-body'></div>").text(message);
            var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
            var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
            var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
            var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
            var modal = $("<div class='modal fade'></div>").append(modalDialog);

            closeButton.on("click", function () {
                if (isSuccess) {
                    location.reload();
                }
                modal.modal("hide");
            });

            modal.on("hidden.bs.modal", function () {
                if (isSuccess) {
                    location.reload();
                }
                modal.remove();
            });

            $("body").append(modal);
            modal.modal("show");
        }

        $inputEditAtr.each(function () {
            const $this = $(this);
            if ($this.is(':checked')) {
                isAnyChecked = true;
                const wrapper = $this.closest('.wrapper-edit-attribute');
                const dropdown = wrapper.find('.dropdown').eq(0);
                const icon = dropdown.find('button.icon-toggle i');
                const $inputPrice = dropdown.find('input');
                const isVal = validatePrice($inputPrice, icon);

                if (!isVal) {
                    isValidPrice = false;
                    return false;
                }

                var value = $this.val().trim();
                var valSplit = value.split('-');
                var sizeId;
                var colorId;
                var importPrice;
                var exportPrice;

                if ($inputPrice.length === 2) {
                    importPrice = $inputPrice.eq(0).val().trim();
                    exportPrice = $inputPrice.eq(1).val().trim();
                }

                if (valSplit.length === 2) {
                    sizeId = valSplit[0];
                    colorId = valSplit[1];
                }

                var productDetail = {
                    "productId": productId,
                    "colorId": colorId,
                    "sizeId": sizeId,
                    "importPrice": importPrice,
                    "exportPrice": exportPrice
                };

                formData.push(productDetail);

            }
        });

        if (!isAnyChecked) {
            showMessage('Please choose any element.', true);
            return false;
        }

        if (!isValidPrice) {
            showMessage('Please edit price of danger edit icons.', true);
            return false;
        }

        $.ajax({
            url: '/mike/api/product/product-detail/edit',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                showModal("Edit success!", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
                showModal("Edit error!", false);
            }
        });

    });

});

$(document).ready(function () {
    const $inputDelAtr = $('#deleteProductDetailForm .form-check-input.delete-attribute-input');
    const $inputCheckAll = $('#deleteProductDetailForm .input-delete-check-all');
    const $messageValidPrice = $('#message-delete-valid-price');
    const $form = $('#deleteProductDetailForm');

    function showMessage(message, isError) {
        const alertClass = isError ? 'danger' : 'success';
        const alertContent = `
            <div class="alert alert-${alertClass} alert-dismissible d-flex position-relative" role="alert">
                <span class="badge badge-center rounded-pill bg-${alertClass} border-label-${alertClass} p-3 me-2"><i class="bx bx-store fs-6"></i></span>
                <div class="d-flex flex-column ps-1">
                    <h6 class="alert-heading d-flex align-items-center mb-1">${isError ? 'Error!!' : 'Success'}</h6>
                    <span>${message}</span>
                </div>
                <button type="button" id ="btn-close-msgErrProductDetail"  class="btn-close position-absolute" data-bs-dismiss="alert" aria-label="Close" style="top:10px;right:5px;"></button>
            </div>
        `;

        $messageValidPrice.html(alertContent);

        // Adjust button style
        const $btnCloseMsgErr = $messageValidPrice.find('button');
        const parentBtnStyle = $btnCloseMsgErr.closest('div').css(['backgroundColor', 'borderColor']);
        $btnCloseMsgErr.css(parentBtnStyle);
    }

    function handleCheckAll(colorId, isChecked) {
        const $wrapperAddAtr = $('#deleteProductDetailForm .wrapper-delete-attribute');
        $wrapperAddAtr.find(`input[data-color-id="${colorId}"].delete-attribute-input`).prop('checked', isChecked).trigger('change');
    }

    $inputCheckAll.on('change', function () {
        const colorId = $(this).val().trim();
        const isChecked = $(this).is(':checked');
        handleCheckAll(colorId, isChecked);
    });

    $form.on('submit', function (event) {
        event.preventDefault();
        var isAnyChecked = false;
        var href = window.location.href;
        var productId = href.substring(href.lastIndexOf("/") + 1);
        var formData = [];

        function showModal(message, isSuccess) {
            var modalHeader = $("<div class='modal-header'></div>").append("<h5 class='modal-title'>" + (isSuccess ? "Success" : "Error") + "</h5>");
            var modalBody = $("<div class='modal-body'></div>").text(message);
            var closeButton = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
            var modalFooter = $("<div class='modal-footer'></div>").append(closeButton);
            var modalContent = $("<div class='modal-content'></div>").append(modalHeader, modalBody, modalFooter);
            var modalDialog = $("<div class='modal-dialog' role='document'></div>").append(modalContent);
            var modal = $("<div class='modal fade'></div>").append(modalDialog);

            closeButton.on("click", function () {
                if (isSuccess) {
                    location.reload();
                }
                modal.modal("hide");
            });

            modal.on("hidden.bs.modal", function () {
                if (isSuccess) {
                    location.reload();
                }
                modal.remove();
            });

            $("body").append(modal);
            modal.modal("show");
        }

        $inputDelAtr.each(function () {
            const $this = $(this);
            if ($this.is(':checked')) {
                isAnyChecked = true;
                var value = $this.val().trim();
                var valSplit = value.split('-');
                var sizeId;
                var colorId;
                var importPrice = 0;
                var exportPrice = 0;

                if (valSplit.length === 2) {
                    sizeId = valSplit[0];
                    colorId = valSplit[1];
                }

                var productDetail = {
                    "productId": productId,
                    "colorId": colorId,
                    "sizeId": sizeId,
                    "importPrice": importPrice,
                    "exportPrice": exportPrice
                };

                formData.push(productDetail);
            }
        });

        if (!isAnyChecked) {
            showMessage('Please choose any element.', true);
            return false;
        }

        $.ajax({
            url: '/mike/api/product/product-detail/delete',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                showModal("Delete success!", true);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
                showModal("Delete error!", false);
            }
        });

    });
});