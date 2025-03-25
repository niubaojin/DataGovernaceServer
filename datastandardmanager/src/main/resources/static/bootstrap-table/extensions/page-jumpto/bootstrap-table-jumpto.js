/**
 * @author zhaoshuxue <zhaoshuxue@163.com>
 * extensions: https://github.com/zhaoshuxue/bootstrap-table/src/extensions/page-jump
 */

(function ($) {
    'use strict';
    $.extend($.fn.bootstrapTable.defaults, {
        // 默认不显示
        paginationShowPageGo: false
    });

    $.extend($.fn.bootstrapTable.locales, {
        pageGo: function () {
            return '前往';
        }
    });
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales);

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initPagination = BootstrapTable.prototype.initPagination;

    BootstrapTable.prototype.initPagination = function() {
        _initPagination.apply(this, Array.prototype.slice.apply(arguments));
        if(this.options.paginationShowPageGo){
            var html = [];

            html.push(
                '<ul class="pagination-jump">',
                '<li class=""><span>' + this.options.pageGo() + '&nbsp;</span></li>',
                '<li class=""><input type="text" class="page-input" value="' + this.options.pageNumber + '"   /></li>',
                '<li class="text"><span>&nbsp;页</span></li>',
                // '<li class="page-go"><a class="jump-go" href="">GO</a></li>',
                '</ul>');
            this.$pagination.find('ul.pagination').after(html.join(''));
            // this.$pagination.find('.page-go').off('click').on('click', $.proxy(this.onPageGo, this));
            this.$pagination.find('.page-input').off('keyup').on('keyup', function(){
                this.value = this.value.length == 1 ? this.value.replace(/[^1-9]/g,'') : this.value.replace(/\D/g,'');
            });
            this.$pagination.find('.page-input').off('keydown').on('keydown', $.proxy(this.onPageGo, this));
            this.$pagination.find('.page-input').off('blur').on('blur', $.proxy(this.onPageGo, this));
        }
    };

    BootstrapTable.prototype.onPageGo = function (event) {
      //  console.log(event.keyCode);
        if(event.keyCode == 8) {
            $('.page-input').val('');
        }
        if(event.keyCode  == 13){
            console.log(this.options.totalPages);
            var $toPage=this.$pagination.find('.page-input');
            if (this.options.pageNumber === +$toPage.val()) {
                return false;
            }
            if(+$toPage.val() > this.options.totalPages) {
                this.selectPage(this.options.totalPages);
            }
            this.selectPage(+$toPage.val());
            return false;
        }else if(event.keyCode == undefined) {
            var $stoPage=this.$pagination.find('.page-input');
            if (this.options.pageNumber === +$stoPage.val()) {
                return false;
            }
            if(+$stoPage.val() > this.options.totalPages) {
                this.selectPage(this.options.totalPages);
            }
            this.selectPage(+$stoPage.val());
        }
    };
})(jQuery);
