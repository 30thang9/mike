$(document).ready(function(){
    const inputAllImportPrice = $('#all-import-price');
    const inputAllExportPrice = $('#all-export-price');
    const btnInputAllImportPrice = $('#btn-all-import-price');
    const btnInputAllExportPrice = $('#btn-all-export-price');

    btnInputAllImportPrice.on('click', function(){
        var inputImportPrice = $('.form-repeater:first [type="number"][data-name="importPrice"][id^="form-repeater-"]');
        inputImportPrice.each(function(){
            $(this).val(inputAllImportPrice.val());
            $(this).trigger('change');
        });
    });

    btnInputAllExportPrice.on('click', function(){
        var inputExportPrice = $('.form-repeater:first [type="number"][data-name="exportPrice"][id^="form-repeater-"]');
        inputExportPrice.each(function(){
            $(this).val(inputAllExportPrice.val());
            $(this).trigger('change');
        });
    });
});
