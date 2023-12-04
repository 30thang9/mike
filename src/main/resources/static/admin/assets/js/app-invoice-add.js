//"use strict";!function(){var e=document.querySelectorAll(".invoice-item-price"),t=document.querySelectorAll(".invoice-item-qty"),n=document.querySelectorAll(".date-picker");e&&e.forEach(function(e){new Cleave(e,{delimiter:"",numeral:!0})}),t&&t.forEach(function(e){new Cleave(e,{delimiter:"",numeral:!0})}),n&&n.forEach(function(e){e.flatpickr({monthSelectorType:"static"})})}(),$(function(){var n,o,a,i,l,r,e=$(".btn-apply-changes"),t=$(".source-item"),c={"App Design":"Designed UI kit & app pages.","App Customization":"Customization & Bug Fixes.","ABC Template":"Bootstrap 4 admin template.","App Development":"Native App Development."};function p(e,t){e.closest(".repeater-wrapper").find(t).text(e.val())}$(document).on("click",".tax-select",function(e){e.stopPropagation()}),e.length&&$(document).on("click",".btn-apply-changes",function(e){var t=$(this);l=t.closest(".dropdown-menu").find("#taxInput1"),r=t.closest(".dropdown-menu").find("#taxInput2"),i=t.closest(".dropdown-menu").find("#discountInput"),o=t.closest(".repeater-wrapper").find(".tax-1"),a=t.closest(".repeater-wrapper").find(".tax-2"),n=$(".discount"),null!==l.val()&&p(l,o),null!==r.val()&&p(r,a),i.val().length&&t.closest(".repeater-wrapper").find(n).text(i.val()+"%")}),t.length&&(t.on("submit",function(e){e.preventDefault()}),t.repeater({show:function(){$(this).slideDown(),[].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]')).map(function(e){return new bootstrap.Tooltip(e)})},hide:function(e){$(this).slideUp()}})),$(document).on("change",".item-details",function(){var e=$(this),t=c[e.val()];e.next("textarea").length?e.next("textarea").val(t):e.after('<textarea class="form-control" rows="2">'+t+"</textarea>")})});

"use strict";

(function() {
  var e = document.querySelectorAll(".invoice-item-price"),
    t = document.querySelectorAll(".invoice-item-qty"),
    n = document.querySelectorAll(".date-picker");

  if (e) {
    e.forEach(function(e) {
      new Cleave(e, {
        delimiter: "",
        numeral: true
      });
    });
  }

  if (t) {
    t.forEach(function(e) {
      new Cleave(e, {
        delimiter: "",
        numeral: true
      });
    });
  }

  if (n) {
    n.forEach(function(e) {
      e.flatpickr({
        monthSelectorType: "static"
      });
    });
  }
})();

$(function() {
  var n, o, a, i, l, r, e = $(".btn-apply-changes"),
    t = $(".source-item"),
    c = {
      "App Design": "Designed UI kit & app pages.",
      "App Customization": "Customization & Bug Fixes.",
      "ABC Template": "Bootstrap 4 admin template.",
      "App Development": "Native App Development."
    };

  function p(e, t) {
    e.closest(".repeater-wrapper").find(t).text(e.val());
  }

  $(document).on("click", ".tax-select", function(e) {
    e.stopPropagation();
  });

  if (e.length) {
    $(document).on("click", ".btn-apply-changes", function(e) {
      var t = $(this);
      l = t.closest(".dropdown-menu").find("#taxInput1");
      r = t.closest(".dropdown-menu").find("#taxInput2");
      i = t.closest(".dropdown-menu").find("#discountInput");
      o = t.closest(".repeater-wrapper").find(".tax-1");
      a = t.closest(".repeater-wrapper").find(".tax-2");
      n = $(".discount");

      if (l.val() !== null) {
        p(l, o);
      }

      if (r.val() !== null) {
        p(r, a);
      }

      if (i.val().length) {
        t.closest(".repeater-wrapper").find(n).text(i.val() + "%");
      }
    });
  }

  if (t.length) {
    t.on("submit", function(e) {
      e.preventDefault();
    });
  }

  t.repeater({
    show: function() {
      $(this).slideDown();

      $('.repeater-wrapper').find('.select2').removeClass('select2-hidden-accessible');
      $('.repeater-wrapper').find('.select2-container').remove();
      $('.repeater-wrapper').find('.select2').select2({
        placeholder: "Select value",
        allowClear: true
      });

      [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]')).map(function(e) {
        return new bootstrap.Tooltip(e);
      });
    },
    hide: function(e) {
      $(this).slideUp();
    }
  });

//  $(document).on("change", ".item-details", function() {
//    var e = $(this),
//      t = c[e.val()];
//    if (e.next("textarea").length) {
//      e.next("textarea").val(t);
//    } else {
//      e.after('<textarea class="form-control" rows="2">' + t + "</textarea>");
//    }
//  });

  $(document).on('DOMNodeInserted', function() {
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
        formSubmitted = true;
        if (!form[0].checkValidity()) {
          event.preventDefault();
        }
      });
    }

    var isSubmitted = false;
    $('.repeater-wrapper').find('.select2').each(function() {
      var $element = $(this);
      if ($element.hasClass('is-valid') || $element.hasClass('is-invalid')) {
        isSubmitted = true;
        return false;
      }
    });

    if (isSubmitted) {
      $('.repeater-wrapper').find('.select2').filter("select").each(function() {
        var $element = $(this);
        var select2 = $element.closest('.wrapper-select-valid-custom').find('.select2');
        var statusIsInvalidFeedback = $element.closest('.wrapper-select-valid-custom').find('.invalid-feedback');
        var statusIsValidFeedback = $element.closest('.wrapper-select-valid-custom').find('.valid-feedback');
        var isInvalid = $element.val() === "";
        select2.toggleClass('is-invalid', isInvalid);
        select2.toggleClass('is-valid', !isInvalid);
        statusIsInvalidFeedback.toggleClass('d-block', isInvalid);
        statusIsValidFeedback.toggleClass('d-block', !isInvalid);
      });
    }

    $('select').each(function() {
      attachSelectEvents($(this));
    });
  });
});



















































































































































































































































































































































































]