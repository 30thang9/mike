$(function () {

    function initializeDropzone(dropzoneSelector) {
        var storageFiles = [];

        $(dropzoneSelector).each(function (index) {
            if (this.dropzone) {
                // Store existing files before destroying
                var existingFiles = this.dropzone.getAcceptedFiles();
                storageFiles[index] = existingFiles;
                this.dropzone.destroy();
            }
        });

        var previewTemplate = `<div class="dz-preview dz-file-preview">
            <div class="dz-details">
                <div class="dz-thumbnail">
                    <img data-dz-thumbnail>
                    <span class="dz-nopreview">No preview</span>
                    <div class="dz-success-mark"></div>
                    <div class="dz-error-mark"></div>
                    <div class="dz-error-message"><span data-dz-errormessage></span></div>
                    <div class="progress">
                        <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuemin="0" aria-valuemax="100" data-dz-uploadprogress></div>
                    </div>
                </div>
                <div class="dz-filename" data-dz-name></div>
                <div class="dz-size" data-dz-size></div>
            </div>
        </div>`;

        $(dropzoneSelector).each(function (index) {
            $(this).dropzone({
                url: "/upload", // You can set a default URL here if needed
                previewTemplate: previewTemplate,
                parallelUploads: 1,
                maxFilesize: 5,
                addRemoveLinks: true,
                init: function () {
                    if (storageFiles[index]) {
                        for (var j = 0; j < storageFiles[index].length; j++) {
                            this.addFile(storageFiles[index][j]);
                        }
                    }
                }
            });
        });

        $(document).trigger("dropzoneInitialized");
    }

    function initializeSelect2(select2Selector) {
        var select2Container = $(select2Selector + "-container");
        select2Container.remove();

        $(select2Selector).select2({
            placeholder: "Placeholder text"
        });

        $(select2Selector + "-container").css("width", "100%");

        $(select2Selector).select2({
            placeholder: "Placeholder text"
        });
    }
    const productDetailStatusFirstVal = $('select[data-name="productDetailStatus"].select2.form-select:first').val();
    const discountPercentFirstVal = $('input[data-name="discountPercent"][type="number"].form-control:first').val();
    var s, i,
        //    e = $(".select2"), e = (e.length && e.each(function () {
        //      var e = $(this);
        //      e.wrap('<div class="position-relative"></div>').select2({
        //        dropdownParent: e.parent(),
        //        placeholder: e.data("placeholder")
        //      })
        //    }),
        e = $(".form-repeater");
    e.length && (s = 2,
        i = 1,
        e.repeater({
            show: function () {
                var a = $(this).find(".form-control, .form-select")
                    , t = $(this).find(".form-label")
                    , m = $(this).find(".form-dropdown-btn")
                    , d = $(this).find(".dropzone")
                    , btn = $(this).find(".btn-modal-dropzone")
                    , md = $(this).find(".modal-dropzone");
                a.each(function (e) {
                    var r = "form-repeater-" + s + "-" + i;
                    var dr = "dropzone-basic-" + s;
                    var mdz_btn = "#modal-repeater-" + s;
                    var mdz = "modal-repeater-" + s;
                    if ($(a[e]).data('name') === "productDetailStatus") {
                        $(a[e]).val(productDetailStatusFirstVal);
                    } else if ($(a[e]).data('name') === "discountPercent") {
                        $(a[e]).val(discountPercentFirstVal);
                    }
                    $(a[e]).attr("id", r),
                        $(t[e]).attr("for", r),
                        $(m[e]).attr("id", r),
                        $(d[e]).attr("id", dr),
                        $(btn[e]).attr("data-bs-target", mdz_btn),
                        $(md[e]).attr("id", mdz),
                        i++
                }),
                    s++,
                    $(this).slideDown();

                initializeSelect2(".form-repeater:first .select2");
                //
                initializeDropzone(".form-repeater:first .dropzone-multiple");
            }
        }));

    var form = e.closest('form');
    var isSubmited = false;
    form.submit(function (event) {
        isSubmited = true;
        if (!form[0].checkValidity()) {
            event.preventDefault();
        }
    });
    //      $(document).ready(function() {
    $(document).on('DOMNodeInserted', function () {
        function handleValidateSelect2(input) {
            const spanSibling = input.siblings('span.select2.select2-container');
            const parent = input.closest('div');
            const validFeedback = parent.siblings('.valid-feedback');
            const invalidFeedback = parent.siblings('.invalid-feedback');
            const spanSelectionType = spanSibling.find('.selection').eq(0).find('.select2-selection').eq(0);
            let isValid = false;

            if (spanSelectionType.hasClass('select2-selection--multiple')) {
                isValid = spanSelectionType.find('.select2-selection__choice').length > 0;
            } else if (spanSelectionType.hasClass('select2-selection--single')) {
                isValid = input.val();
            }

            if (!isValid) {
                spanSibling.addClass('is-invalid');
                spanSibling.removeClass('is-valid');
                invalidFeedback.addClass('d-block');
                validFeedback.removeClass('d-block');
            } else {
                spanSibling.addClass('is-valid');
                spanSibling.removeClass('is-invalid');
                validFeedback.addClass('d-block');
                invalidFeedback.removeClass('d-block');
            }
        }

        function attachSelectEvents(selectInput) {
            var form = selectInput.closest('form');
            var formSubmitted = false;

            selectInput.change(function () {
                if (formSubmitted) {
                    handleValidateSelect2(selectInput);
                }
            });

            form.submit(function (event) {
                handleValidateSelect2(selectInput);
                formSubmitted = true;
                if (!form[0].checkValidity()) {
                    event.preventDefault();
                }
            });
        }

        $('.form-repeater:first select.select2').each(function () {
            if (isSubmited) {
                handleValidateSelect2($(this));
            }
            attachSelectEvents($(this));
        });

        function handleValidateDropzone(myDropzone, dropzone) {
            var validFeedback = dropzone.siblings('.valid-feedback');
            var invalidFeedback = dropzone.siblings('.invalid-feedback');
            var hasFile = myDropzone && myDropzone.files && myDropzone.files.length > 0;
            var buttonSib = dropzone.closest('.modal').siblings('button');

            if (hasFile) {
                validFeedback.addClass('d-block');
                invalidFeedback.removeClass('d-block');
                dropzone.addClass('border-dashed border-success');
                dropzone.removeClass('border-dashed border-danger');
                buttonSib.addClass('btn-label-success');
                buttonSib.removeClass('btn-label-primary');
                buttonSib.removeClass('btn-label-danger');
            } else {
                validFeedback.removeClass('d-block');
                invalidFeedback.addClass('d-block');
                dropzone.removeClass('border-dashed border-success');
                dropzone.addClass('border-dashed border-danger');
                buttonSib.addClass('btn-label-danger');
                buttonSib.removeClass('btn-label-primary');
                buttonSib.removeClass('btn-label-success');
            }

            return hasFile;
        }

        function attachDropzoneEvents(dropzone) {
            if (dropzone.hasClass('dz-clickable')) {
                var myDropzone = dropzone.dropzone;

                var form = dropzone.closest('form');
                var formSubmitted = false;
                var hasFile = false;
                if (typeof myDropzone.on === "function") {
                    myDropzone.on("addedfile", function (file) {
                        if (formSubmitted) {
                            hasFile = handleValidateDropzone(myDropzone, dropzone);
                        }
                    });

                    myDropzone.on("removedfile", function (file) {
                        if (formSubmitted) {
                            hasFile = handleValidateDropzone(myDropzone, dropzone);
                        }
                    });

                    form.on('submit', function (event) {
                        hasFile = handleValidateDropzone(myDropzone, dropzone);
                        formSubmitted = true;

                        if (hasFile) {
                            //
                        } else {
                            event.preventDefault();
                        }
                    });
                }
            }
        }

        //          $(document).on("dropzoneInitialized", function () {
        $('.form-repeater:first .dropzone').each(function () {
            var dropzone = $(this);
            if (dropzone && dropzone.dropzone) {
                if (isSubmited) {
                    if (dropzone.hasClass('dz-clickable')) {
                        var myDropzone = dropzone.dropzone;
                        handleValidateDropzone(myDropzone, dropzone);
                    }
                }
                attachDropzoneEvents(dropzone);
            }
        });
        //          });
    });
    //      });
});