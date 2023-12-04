$(document).ready(function () {
    const inputAllImportPrice = $('#all-import-price');
    const inputAllExportPrice = $('#all-export-price');
    const btnInputAllImportPrice = $('#btn-all-import-price');
    const btnInputAllExportPrice = $('#btn-all-export-price');
    const selectAllSize = $('#all-size');
    const selectAllSizeSiblingSpan = selectAllSize.siblings('span');
    const btnSelectAllSize = $('#btn-all-size');

    const inputAllDiscount = $('#all-discount');
    const btnInputAllDiscount = $('#btn-all-discount');

    btnInputAllImportPrice.on('click', function () {
        var inputImportPrice = $('.form-repeater:first [type="number"][data-name="importPrice"][id^="form-repeater-"]');
        inputImportPrice.each(function () {
            $(this).val(inputAllImportPrice.val());
            $(this).trigger('change');
        });
    });

    btnInputAllExportPrice.on('click', function () {
        var inputExportPrice = $('.form-repeater:first [type="number"][data-name="exportPrice"][id^="form-repeater-"]');
        inputExportPrice.each(function () {
            $(this).val(inputAllExportPrice.val());
            $(this).trigger('change');
        });
    });

    selectAllSizeSiblingSpan.addClass('border-primary');

    btnSelectAllSize.on('click', function () {

        var selectedData = selectAllSize.select2('data');
        var selectedValues = $.map(selectedData, function (option) {
            return option.id;
        });

        var sizeSelector = $('.form-repeater:first select[data-name="size"][id^="form-repeater-"].select2.form-select');
        sizeSelector.each(function () {
            $(this).val(selectedValues).trigger('change');
        });

    });

    btnInputAllDiscount.on('click', function () {
        var inputDiscountPercent = $('.form-repeater:first [type="number"][data-name="discountPercent"][id^="form-repeater-"]');
        inputDiscountPercent.each(function () {
            $(this).val(inputAllDiscount.val());
            $(this).trigger('change');
        });
    });

    $('.input-discount').on('input', function () {
        var input = $(this);
        var value = parseFloat(input.val());
        if (value < 0 || value > 100) {
            var adjustedValue = parseFloat(value.toFixed(1));
            input.val(Math.min(100.0, Math.max(0.0, adjustedValue)));
        }
    });

});
