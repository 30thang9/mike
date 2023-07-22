"use strict";

function initializeDropzoneSingle(dropzoneClass, previewTemplate) {
  var dropzones = document.querySelectorAll("." + dropzoneClass);

  Array.prototype.forEach.call(dropzones, function(dropzone) {
    new Dropzone(dropzone, {
      url: "/upload",
      previewTemplate: previewTemplate,
      parallelUploads: 1,
      maxFilesize: 5,
      addRemoveLinks: true,
      maxFiles: 1,
      init: function() {
          this.on("addedfile", function(file) {
            if (this.files.length > 1) {
              // Nếu đã có một tệp tin khác được tải lên trước đó, xóa tệp tin đó
              this.removeFile(this.files[0]);
            }
          });
        }
    });
  });
}

function initializeDropzoneMulti(dropzoneClass, previewTemplate) {
  var dropzones = document.querySelectorAll("." + dropzoneClass);

  Array.prototype.forEach.call(dropzones, function(dropzone) {
    new Dropzone(dropzone, {
      url: "/upload",
      previewTemplate: previewTemplate,
      parallelUploads: 1,
      maxFilesize: 5,
      addRemoveLinks: true
    });
  });
}

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

// Khởi tạo Dropzone cho các khối HTML có class "dropzone-single" và giới hạn tệp tin là 1
initializeDropzoneSingle("dropzone-single", previewTemplate);

// Khởi tạo Dropzone cho các khối HTML có class "dropzone-multi" và không giới hạn số lượng tệp tin
initializeDropzoneMulti("dropzone-multi", previewTemplate);
