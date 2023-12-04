'use strict';

(function () {
    const href = window.location.pathname;
    const rut = href.substring(href.lastIndexOf('mike/') + "mike/".length);
    if (rut === "/admin") {
        $('.menu-item').eq(0).addClass('active');
    } else {
        const route = rut.substring(rut.lastIndexOf('admin/') + "admin/".length);
        const r = route.split('/');
        var menuItems = $('.menu-item').filter(function () {
            const menuItem = $(this);
            return r.length - 1 === menuItem.parents('.menu-item').length && r[r.length - 1] === menuItem.find('>.menu-link').data('route');
        });
        if (menuItems.length === 1) {
            if (r.length > 1) {
                const parentMenuItems = menuItems.parents('.menu-item').get().reverse();
                var match = true;
                let i = 0;
                $.each(parentMenuItems, function (index, parentMenuItem) {
                    const menuLink = $(parentMenuItem).find('>.menu-link');
                    const dataRoute = menuLink.data('route');
                    if (r[i] !== dataRoute) {
                        match = false;
                    }
                    i++;
                });
                if (match) {
                    menuItems.addClass('active');
                    $(parentMenuItems).addClass('active');
                    $(parentMenuItems).addClass('open');
                }
            } else if (r.length === 1) {
                menuItems.addClass('active');
            }
        } else if (menuItems.length > 1) {
            menuItems.each(function () {
                const menuItemv = $(this);
                var isBreak = false;
                if (r.length > 1) {
                    const parentMenuItems = menuItemv.parents('.menu-item').get().reverse();
                    var match = true;
                    let i = 0;
                    $.each(parentMenuItems, function (index, parentMenuItem) {
                        const menuLink = $(parentMenuItem).find('>.menu-link');
                        const dataRoute = menuLink.data('route');
                        if (r[i] !== dataRoute) {
                            match = false;
                        }
                        i++;
                    });
                    if (match) {
                        menuItemv.addClass('active');
                        $(parentMenuItems).addClass('active');
                        $(parentMenuItems).addClass('open');
                        isBreak = true;
                    }
                } else if (r.length === 1) {
                    menuItemv.addClass('active');
                    isBreak = true;
                }

                if (isBreak) {
                    return false;
                }
            });
        }
    }
})();
