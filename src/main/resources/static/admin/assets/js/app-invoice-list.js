"use strict";
$(function () {
    var a, e = $(".product-list-table");
    var accessToken = localStorage.getItem('accessToken');
    e.length && (a = e.DataTable({
        ajax: {
            url: "/mike/api/product/list",
            method: "GET",
            headers: {
                "Authorization": "Bearer " + accessToken == null ? "" : accessToken
            },
            dataSrc: ""
        },
        columns: [
            {
                data: ""
            },
            {
                data: "id"
            },
            {
                data: "name"
            },
            //            {
            //                data: "descriptions"
            //            },
            {
                data: "urlAvatar"
            },
            {
                data: "objectCategory"
            },
            {
                data: "productCategory"
            },
            {
                data: "productStatus"
            },
            {
                data: "discountStatus"
            },
            {
                data: "discountPercent"
            },
            {
                data: "action"
            }
        ],
        columnDefs: [
            {
                className: "control",
                responsivePriority: 2,
                searchable: !1,
                targets: 0,
                render: function (a, e, t, s) {
                    return "";
                }
            },
            {
                targets: 1,
                render: function (a, e, t, s) {
                    return '<a href="/mike/admin/product/detail/' + t.id + '">#' + t.id + "</a>";
                }
            },
            {
                targets: 2,
                render: function (a, e, t, s) {
                    return t.name;
                }
            },
            //            {
            //                targets: 3,
            //                render: function (a, e, t, s) {
            //                    return t.descriptions;
            //                }
            //            },
            {
                targets: 3,
                render: function (a, e, t, s) {
                    return '<img style="height:50px; object-fit:cover;" src="' + t.urlAvatar + '" alt="Avatar" class="rounded-circle avatar-img">';
                }
            },
            {
                targets: 4,
                render: function (a, e, t, s) {
                    return t.objectCategory.name;
                }
            },
            {
                targets: 5,
                render: function (a, e, t, s) {
                    return t.productCategory.name;
                }
            },
            {
                targets: 6,
                render: function (a, e, t, s) {
                    var statusBadgeClass = "";
                    switch (t.productStatus) {
                        case "AVAILABLE":
                            statusBadgeClass = "bg-label-success";
                            break;
                        case "OUT_OF_STOCK":
                            statusBadgeClass = "bg-label-warning";
                            break;
                        case "DISCONTINUED":
                            statusBadgeClass = "bg-label-danger";
                            break;
                    }
                    return '<span class="badge ' + statusBadgeClass + '">' + t.productStatus + '</span>';
                }
            },
            {
                targets: 7,
                render: function (a, e, t, s) {
                    var statusBadgeClass = "";
                    switch (t.discountStatus) {
                        case "ACTIVE":
                            statusBadgeClass = "bg-label-success";
                            break;
                        case "INACTIVE":
                            statusBadgeClass = "bg-label-danger";
                            break;
                    }
                    return '<span class="badge ' + statusBadgeClass + '">' + t.discountStatus + '</span>';
                }
            },
            {
                targets: 8,
                render: function (a, e, t, s) {
                    return t.discountPercent;
                }
            },
            {
                targets: -1,
                title: "Actions",
                searchable: !1,
                orderable: !1,
                render: function (a, e, t, s) {
                    //                    return '<div class="d-inline-block"><a href="javascript:;" class="btn btn-sm btn-icon dropdown-toggle hide-arrow" data-bs-toggle="dropdown"><i class="bx bx-dots-vertical-rounded"></i></a><div class="dropdown-menu dropdown-menu-end m-0"><a href="javascript:;" class="dropdown-item">Details</a><a href="javascript:;" class="dropdown-item">Archive</a><div class="dropdown-divider"></div><a href="javascript:;" class="dropdown-item text-danger delete-record">Delete</a></div></div><a href="/mike/admin/product/edit/' + t.id + '" class="btn btn-sm btn-icon item-edit"><i class="bx bxs-edit"></i></a>';
                    //                    return '<div class="d-flex align-items-center"><a href="javascript:;" data-bs-toggle="tooltip" class="text-body" data-bs-placement="top" title="Send Mail"><i class="bx bx-send mx-1"></i></a><a href="app-invoice-preview.html" data-bs-toggle="tooltip" class="text-body" data-bs-placement="top" title="Preview Invoice"><i class="bx bx-show mx-1"></i></a><div class="dropdown"><a href="javascript:;" class="btn dropdown-toggle hide-arrow text-body p-0" data-bs-toggle="dropdown"><i class="bx bx-dots-vertical-rounded"></i></a><div class="dropdown-menu dropdown-menu-end"><a href="javascript:;" class="dropdown-item">Download</a><a href="app-invoice-edit.html" class="dropdown-item">Edit</a><a href="javascript:;" class="dropdown-item">Duplicate</a><div class="dropdown-divider"></div><a href="javascript:;" class="dropdown-item delete-record text-danger">Delete</a></div></div></div>';
                    return '<div class="d-flex align-items-center"><a href="/mike/admin/product/detail/' + t.id + '" data-bs-toggle="tooltip" class="text-body" data-bs-placement="top" title="Detail"><i class="bx bx-show mx-1"></i></a><div class="dropdown"><a href="javascript:;" class="btn dropdown-toggle hide-arrow text-body p-0" data-bs-toggle="dropdown"><i class="bx bx-dots-vertical-rounded"></i></a><div class="dropdown-menu dropdown-menu-end"><a href="javascript:;" class="dropdown-item">Download</a><a href="/mike/admin/product/edit/' + t.id + '" class="dropdown-item">Edit</a><a href="javascript:;" class="dropdown-item">Duplicate</a><div class="dropdown-divider"></div><button data-product-id="' + t.id + '" class="dropdown-item delete-record text-danger btn-delete">Delete</button></div></div></div>';

                }
            }
        ],
        order: [[1, "desc"]],
        dom: `
           <"row mt-4 mb-3 ms-2 me-3 d-md-none"
                 <"col-12 d-flex align-items-center justify-content-start px-3 gap-2"
                   <"_title_">
                 >
                 <"col-12 d-flex align-items-center justify-content-start pe-3 gap-2"
                   <"wrapper-btn-collapse">
                 >
           >
           <"wrapper_control"
              <"_divider_ d-md-none">
              <"row mt-0 mt-md-4 mb-3 ms-2 me-3"
                <"col-12 col-md-6 d-none d-md-flex align-items-center justify-content-start justify-content-md-start gap-2"
                  <"_title_">
                >
                <"col-12 col-md-6 d-flex align-items-center justify-content-start justify-content-md-end flex-md-row pe-3 gap-md-2"
                  <"dt-action-buttons text-xl-end text-lg-start text-md-end text-start mt-md-0 mt-3"B>
                >
              >
              <"row ms-2 me-3"
                <"col-12 col-md-2 d-flex align-items-center justify-content-start justify-content-md-start gap-2 pb-3 py-md-0" l>
                <"col-12 col-md-10 d-flex align-items-center justify-content-start justify-content-md-end flex-column flex-md-row pe-3 gap-md-2"
                  <"wrapper_search d-flex align-items-center justify-content-start justify-content-md-end"f>
                  <"wrapper_select d-flex align-items-center justify-content-start justify-content-md-end flex-md-row gap-2"
                    <"product_status my-3">
                    <"discount_status my-3">
                  >
                >
              >
           >
           t
           <"row mx-2"
              <"col-sm-12 col-md-6" i>
              <"col-sm-12 col-md-6" p>
           >
        `,

        //        dom: "<'row'<'col-6 d-flex align-items-center justify-content-start'><'col-6 d-flex align-items-center justify-content-end flex-column flex-md-row pe-3 gap-md-2'<'my-3'B>>>" +
        //             "<'row w-100'<'col-6 col-md-6 d-flex align-items-center justify-content-center justify-content-md-start gap-2'l><'col-6 col-md-6 d-flex align-items-center justify-content-end flex-column flex-md-row pe-3 gap-md-2'f<'product_status'><'discount_status'>>>" +
        //             "<'row'<'col-12't>>" +
        //             "<'row'<'col-12'p>>",


        language: {
            sLengthMenu: "_MENU_",
            search: "",
            searchPlaceholder: "Search Product"
        },
        displayLength: 5,
        lengthMenu: [5, 10, 25, 50, 75, 100],
        buttons: [{
            extend: "collection",
            className: "btn btn-label-primary dropdown-toggle me-2",
            text: '<i class="bx bx-export me-sm-1"></i> <span class="d-none d-sm-inline-block">Export</span>',
            buttons: [{
                extend: "print",
                text: '<i class="bx bx-printer me-1" ></i>Print',
                className: "dropdown-item",
                exportOptions: {
                    columns: [2, 4, 5, 6, 7, 8],
                    //                    format: {
                    //                        body: function(e, t, a) {
                    //                            var s;
                    //                            return e.length <= 0 ? e : (e = $.parseHTML(e),
                    //                            s = "",
                    //                            $.each(e, function(e, t) {
                    //                                void 0 !== t.classList && t.classList.contains("user-name") ? s += t.lastChild.firstChild.textContent : void 0 === t.innerText ? s += t.textContent : s += t.innerText
                    //                            }),
                    //                            s)
                    //                        }
                    //                    }
                },
                customize: function (e) {
                    $(e.document.body).css("color", config.colors.headingColor).css("border-color", config.colors.borderColor).css("background-color", config.colors.bodyBg),
                        $(e.document.body).find("table").addClass("compact").css("color", "inherit").css("border-color", "inherit").css("background-color", "inherit")
                }
            }, {
                extend: "csv",
                text: '<i class="bx bx-file me-1" ></i>Csv',
                className: "dropdown-item",
                exportOptions: {
                    columns: [2, 4, 5, 6, 7, 8],
                    //                    format: {
                    //                        body: function(e, t, a) {
                    //                            var s;
                    //                            return e.length <= 0 ? e : (e = $.parseHTML(e),
                    //                            s = "",
                    //                            $.each(e, function(e, t) {
                    //                                void 0 !== t.classList && t.classList.contains("user-name") ? s += t.lastChild.firstChild.textContent : void 0 === t.innerText ? s += t.textContent : s += t.innerText
                    //                            }),
                    //                            s)
                    //                        }
                    //                    }
                }
            }, {
                extend: "excel",
                text: '<i class="bx bxs-file-export me-1"></i>Excel',
                className: "dropdown-item",
                exportOptions: {
                    columns: [2, 4, 5, 6, 7, 8],
                    //                    format: {
                    //                        body: function(e, t, a) {
                    //                            var s;
                    //                            return e.length <= 0 ? e : (e = $.parseHTML(e),
                    //                            s = "",
                    //                            $.each(e, function(e, t) {
                    //                                void 0 !== t.classList && t.classList.contains("user-name") ? s += t.lastChild.firstChild.textContent : void 0 === t.innerText ? s += t.textContent : s += t.innerText
                    //                            }),
                    //                            s)
                    //                        }
                    //                    }
                }
            }, {
                extend: "pdf",
                text: '<i class="bx bxs-file-pdf me-1"></i>Pdf',
                className: "dropdown-item",
                exportOptions: {
                    columns: [2, 4, 5, 6, 7, 8],
                    format: {
                        //                        body: function(e, t, a) {
                        //                            var s;
                        //                            return e.length <= 0 ? e : (e = $.parseHTML(e),
                        //                            s = "",
                        //                            $.each(e, function(e, t) {
                        //                                void 0 !== t.classList && t.classList.contains("user-name") ? s += t.lastChild.firstChild.textContent : void 0 === t.innerText ? s += t.textContent : s += t.innerText
                        //                            }),
                        //                            s)
                        //                        }
                    }
                }
            }, {
                extend: "copy",
                text: '<i class="bx bx-copy me-1" ></i>Copy',
                className: "dropdown-item",
                exportOptions: {
                    columns: [2, 4, 5, 6, 7, 8],
                    format: {
                        //                        body: function(e, t, a) {
                        //                            var s;
                        //                            return e.length <= 0 ? e : (e = $.parseHTML(e),
                        //                            s = "",
                        //                            $.each(e, function(e, t) {
                        //                                void 0 !== t.classList && t.classList.contains("user-name") ? s += t.lastChild.firstChild.textContent : void 0 === t.innerText ? s += t.textContent : s += t.innerText
                        //                            }),
                        //                            s)
                        //                        }
                    }
                }
            }]
        },
        {
            text: '<i class="bx bx-plus me-sm-1"></i> <span class="d-none d-sm-inline-block">Add</span>',
            className: "create-new btn btn-primary",
            action: function () {
                window.location.href = "/mike/admin/product/add";
            }
        }
        ],
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal({
                    header: function (a) {
                        return "Details of " + a.data().name
                    }
                    //                    ,
                    //                    onHide: function (modal) {
                    //                        modal.remove();
                    //                    }
                }),
                type: "column",
                renderer: function (a, e, t) {
                    t = $.map(t, function (a, e) {
                        return "" !== a.title ? '<tr data-dt-row="' + a.rowIndex + '" data-dt-column="' + a.columnIndex + '"><td>' + a.title + ":</td> <td>" + a.data + "</td></tr>" : ""
                    }).join("");
                    return !!t && $('<table class="table"/><tbody />').append(t)
                }
            }
        },
        initComplete: function () {
            $('<h4 class="fw-bold">List Product</h4>').appendTo("._title_");
            $('<button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#wrapperCollapse" aria-expanded="false" aria-controls="wrapperCollapse"><i class="bx bx-collapse-vertical"></i></button>').appendTo(".wrapper-btn-collapse");
            $('<hr class="my-0">').appendTo("._divider_");

            if ($(window).width() < 768) { // 768px là giới hạn cho kích thước md
                $(".wrapper_control").addClass("collapse");
                $(".wrapper_control").attr("id", "wrapperCollapse");
                $(".wrapper_search input").addClass("mx-0");
                $(".wrapper_search").addClass("w-100");
                $(".wrapper_select").addClass("w-100");
            } else {
                $(".wrapper_control").removeClass("collapse");
                $(".wrapper_control").removeAttr("id", "wrapperCollapse");
                $(".wrapper_search input").removeClass("mx-0");
                $(".wrapper_search").removeClass("w-100");
                $(".wrapper_select").removeClass("w-100");
            }

            $(window).on("resize", function () {
                var windowWidth = $(window).width();
                if (windowWidth < 768) { // 768px là giới hạn cho kích thước md
                    $(".wrapper_control").addClass("collapse");
                    $(".wrapper_control").attr("id", "wrapperCollapse");
                    $(".wrapper_search input").addClass("mx-0");
                    $(".wrapper_search").addClass("w-100");
                    $(".wrapper_select").addClass("w-100");
                } else {
                    $(".wrapper_control").removeClass("collapse");
                    $(".wrapper_control").removeAttr("id", "wrapperCollapse");
                    $(".wrapper_search input").removeClass("mx-0");
                    $(".wrapper_search").removeClass("w-100");
                    $(".wrapper_select").removeClass("w-100");
                }
            });

            this.api().columns(6).every(function () {
                var e = this
                    , t = $('<select id="ProductStatus" class="form-select"><option value=""> Product Status </option></select>').appendTo(".product_status").on("change", function () {
                        var a = $.fn.dataTable.util.escapeRegex($(this).val());
                        e.search(a ? "^" + a + "$" : "", !0, !1).draw()
                    });
                e.data().unique().sort().each(function (a, e) {
                    t.append('<option value="' + a + '" class="text-capitalize">' + a + "</option>")
                })
            });

            this.api().columns(7).every(function () {
                var e = this
                    , t = $('<select id="DiscountStatus" class="form-select"><option value=""> Discount Status </option></select>').appendTo(".discount_status").on("change", function () {
                        var a = $.fn.dataTable.util.escapeRegex($(this).val());
                        e.search(a ? "^" + a + "$" : "", !0, !1).draw()
                    });
                e.data().unique().sort().each(function (a, e) {
                    t.append('<option value="' + a + '" class="text-capitalize">' + a + "</option>")
                })
            });
        }
    })),
        e.on("draw.dt", function () {
            [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]')).map(function (a) {
                return new bootstrap.Tooltip(a, {
                    boundary: document.body
                })
            })
        }),
        //        $(".product-list-table tbody").on("click", ".delete-record", function () {
        //            a.row($(this).parents("tr")).remove().draw()
        //        }),
        setTimeout(() => {
            $(".dataTables_filter .form-control").removeClass("form-control-sm"),
                $(".dataTables_length .form-select").removeClass("form-select-sm")
        }
            , 300)
});
