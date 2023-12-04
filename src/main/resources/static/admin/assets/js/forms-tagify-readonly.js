"use strict";

(function () {
  var readonlyElements = document.querySelectorAll(".tagify-readonly");

  readonlyElements.forEach(function (readonlyElement) {
    var tagifyReadonly = new Tagify(readonlyElement, {
      whitelist: [], // Set an empty whitelist for read-only mode
      maxTags: 10, // Set the maximum number of tags
      dropdown: {
        enabled: 0, // Disable the dropdown
        closeOnSelect: false,
      },
      readonly: true, // Enable read-only mode
    });
  });
})();
