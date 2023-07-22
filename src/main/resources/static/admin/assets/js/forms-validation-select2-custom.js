function validateSelect(selectInput, selectSelect2, statusIsInvalidFeedback, statusIsValidFeedback) {
  var selectValue = selectInput.val();
  var isInvalid = selectValue === "";

  statusIsInvalidFeedback.toggleClass('d-block', isInvalid);
  statusIsValidFeedback.toggleClass('d-block', !isInvalid);
  selectSelect2.toggleClass('is-invalid', isInvalid);
  selectSelect2.toggleClass('is-valid', !isInvalid);
}

function attachSelectEvents(selectInput) {
  var form = selectInput.closest('form');
  var wrapper = selectInput.closest('.wrapper-select-valid-custom');
  var selectSelect2 = wrapper.find('.select2');
  var statusIsInvalidFeedback = wrapper.find('.invalid-feedback');
  var statusIsValidFeedback = wrapper.find('.valid-feedback');
  var formSubmitted = false;

  selectInput.change(function() {
    if (formSubmitted) {
      validateSelect(selectInput, selectSelect2, statusIsInvalidFeedback, statusIsValidFeedback);
    }
  });

  form.submit(function(event) {
    validateSelect(selectInput, selectSelect2, statusIsInvalidFeedback, statusIsValidFeedback);
    formSubmitted = true; // Set the flag to true
    if (!form[0].checkValidity()) {
      event.preventDefault(); // Prevent form submission
    }
  });
}


$(document).ready(function() {
  $('select').each(function() {
    attachSelectEvents($(this));
  });
});
//
//$(document).on('DOMNodeInserted', function() {
//  var isSubmitted = false;
//  $('.repeater-wrapper').find('.select2').each(function() {
//    var $element = $(this);
//    if ($element.hasClass('is-valid') || $element.hasClass('is-invalid')) {
//      isSubmitted = true;
//      return false; // Exit the loop
//    }
//  });
//
//  if (isSubmitted) {
//    $('.repeater-wrapper').find('.select2').filter("select").each(function() {
//      var $element = $(this);
//      var select2 = $element.closest('.wrapper-select-valid-custom').find('.select2');
//      var statusIsInvalidFeedback = $element.closest('.wrapper-select-valid-custom').find('.invalid-feedback');
//      var statusIsValidFeedback = $element.closest('.wrapper-select-valid-custom').find('.valid-feedback');
//      var isInvalid = $element.val() === "";
//      select2.toggleClass('is-invalid', isInvalid);
//      select2.toggleClass('is-valid', !isInvalid);
//      statusIsInvalidFeedback.toggleClass('d-block', isInvalid);
//      statusIsValidFeedback.toggleClass('d-block', !isInvalid);
//    });
//  }
//
//  $('select').each(function() {
//    attachSelectEvents($(this));
//  });
//});

