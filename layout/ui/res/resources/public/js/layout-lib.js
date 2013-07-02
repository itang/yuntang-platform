/**
{:date "2013-04-13 19:46:20"}
*/
;/**
resources/public/mooncake/custom-plugins/bootstrap-fileinput.min.js
*/
/*
 * MoonCake v1.3.1 - FileInput Plugin JS
 *
 * This file is part of MoonCake, an Admin template build for sale at ThemeForest.
 * For questions, suggestions or support request, please mail me at maimairel@yahoo.com
 *
 * Development Started:
 * July 28, 2012
 * Last Update:
 * December 07, 2012
 *
 * 'Highly configurable' mutable plugin boilerplate
 * Author: @markdalgleish
 * Further changes, comments: @addyosmani
 * Licensed under the MIT license
 *
 */

;(function(b,e,f,d){function c(a,b){arguments.length&&this._init(a,b)}c.prototype={defaults:{placeholder:"No file selected...",buttontext:"Browse...",inputSize:"large"},_init:function(a,c){this.element=b(a);this.options=b.extend({},this.defaults,c,this.element.data());this._build()},_build:function(){this.element.css({position:"absolute",top:0,right:0,margin:0,cursor:"pointer",fontSize:"99px",opacity:0,filter:"alpha(opacity=0)"}).on("change.fileupload",b.proxy(this._change,this));this.container=b('<div class="fileinput-holder input-append"></div>').append(b('<div class="fileinput-preview uneditable-input" style="cursor: text; text-overflow: ellipsis; "></div>').addClass("input-"+
this.options.inputSize).text(this.options.placeholder)).append(b('<span class="fileinput-btn btn" style="overflow: hidden; position: relative; cursor: pointer; "></span>').text(this.options.buttontext)).insertAfter(this.element);this.element.appendTo(this.container.find(".fileinput-btn"))},_change:function(a){(a=a.target.files!==d?a.target.files[0]:{name:a.target.value.replace(/^.+\\/,"")})&&this.container.find(".fileinput-preview ").text(a.name)}};b.fn.fileInput=function(a){return this.each(function(){new c(this,
a)})};b(function(){b('[data-provide="fileinput"]').each(function(){var a=b(this);a.fileInput(a.data())})})})(jQuery,window,document);
;/**
resources/public/mooncake/custom-plugins/bootstrap-inputmask.min.js
*/
/* ===========================================================
 * bootstrap-inputmask.js j1
 * http://twitter.github.com/bootstrap/javascript.html#tooltips
 * Based on Masked Input plugin by Josh Bush (digitalbush.com)
 * ===========================================================
 * Copyright 2012 Jasny BV, Netherlands.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

!function(c){var i=void 0!==window.orientation,j=-1<navigator.userAgent.toLowerCase().indexOf("android");c.mask={definitions:{9:"[0-9]",a:"[A-Za-z]","*":"[A-Za-z0-9]"},dataName:"rawMaskFn"};var g=function(a,b){j||(this.$element=c(a),this.mask=b.mask,this.options=c.extend({},c.fn.inputmask.defaults,b),this.init(),this.listen(),this.checkVal())};g.prototype={init:function(){var a=c.extend(!0,{},c.mask.definitions,this.options.definitions),b=this.mask.length;this.tests=[];this.partialPosition=this.mask.length;
this.firstNonMaskPos=null;c.each(this.mask.split(""),c.proxy(function(f,d){"?"==d?(b--,this.partialPosition=f):a[d]?(this.tests.push(RegExp(a[d])),null===this.firstNonMaskPos&&(this.firstNonMaskPos=this.tests.length-1)):this.tests.push(null)},this));this.buffer=c.map(this.mask.split(""),c.proxy(function(b){if("?"!=b)return a[b]?this.options.placeholder:b},this));this.focusText=this.$element.val();this.$element.data(c.mask.dataName,c.proxy(function(){return c.map(this.buffer,function(a,b){return this.tests[b]&&
a!=this.options.placeholder?a:null}).join("")},this))},listen:function(){if(!this.$element.attr("readonly")){var a=(c.browser.msie?"paste":"input")+".mask";this.$element.on("unmask",c.proxy(this.unmask,this)).on("focus.mask",c.proxy(this.focusEvent,this)).on("blur.mask",c.proxy(this.blurEvent,this)).on("keydown.mask",c.proxy(this.keydownEvent,this)).on("keypress.mask",c.proxy(this.keypressEvent,this)).on(a,c.proxy(this.pasteEvent,this))}},caret:function(a,b){if(0!==this.$element.length){if("number"==
typeof a)return b="number"==typeof b?b:a,this.$element.each(function(){if(this.setSelectionRange)this.setSelectionRange(a,b);else if(this.createTextRange){var d=this.createTextRange();d.collapse(!0);d.moveEnd("character",b);d.moveStart("character",a);d.select()}});if(this.$element[0].setSelectionRange)a=this.$element[0].selectionStart,b=this.$element[0].selectionEnd;else if(document.selection&&document.selection.createRange)var f=document.selection.createRange(),a=0-f.duplicate().moveStart("character",
-1E5),b=a+f.text.length;return{begin:a,end:b}}},seekNext:function(a){for(var b=this.mask.length;++a<=b&&!this.tests[a];);return a},seekPrev:function(a){for(;0<=--a&&!this.tests[a];);return a},shiftL:function(a,b){var f=this.mask.length;if(!(0>a)){for(var d=a,c=this.seekNext(b);d<f;d++)if(this.tests[d]){if(c<f&&this.tests[d].test(this.buffer[c]))this.buffer[d]=this.buffer[c],this.buffer[c]=this.options.placeholder;else break;c=this.seekNext(c)}this.writeBuffer();this.caret(Math.max(this.firstNonMaskPos,
a))}},shiftR:function(a){for(var b=this.mask.length,c=this.options.placeholder;a<b;a++)if(this.tests[a]){var d=this.seekNext(a),e=this.buffer[a];this.buffer[a]=c;if(d<b&&this.tests[d].test(e))c=e;else break}},unmask:function(){this.$element.unbind(".mask").removeData("inputmask")},focusEvent:function(){this.focusText=this.$element.val();var a=this.mask.length,b=this.checkVal();this.writeBuffer();var f=this,d=function(){b==a?f.caret(0,b):f.caret(b)};c.browser.msie?d():setTimeout(d,0)},blurEvent:function(){this.checkVal();
this.$element.val()!=this.focusText&&this.$element.trigger("change")},keydownEvent:function(a){a=a.which;if(8==a||46==a||i&&127==a){var b=this.caret(),c=b.begin,b=b.end;0===b-c&&(c=46!=a?this.seekPrev(c):b=this.seekNext(c-1),b=46==a?this.seekNext(b):b);this.clearBuffer(c,b);this.shiftL(c,b-1);return!1}if(27==a)return this.$element.val(this.focusText),this.caret(0,this.checkVal()),!1},keypressEvent:function(a){var b=this.mask.length,c=a.which,d=this.caret();if(a.ctrlKey||a.altKey||a.metaKey||32>c)return!0;
if(c)return 0!==d.end-d.begin&&(this.clearBuffer(d.begin,d.end),this.shiftL(d.begin,d.end-1)),a=this.seekNext(d.begin-1),a<b&&(b=String.fromCharCode(c),this.tests[a].test(b)&&(this.shiftR(a),this.buffer[a]=b,this.writeBuffer(),b=this.seekNext(a),this.caret(b))),!1},pasteEvent:function(){var a=this;setTimeout(function(){a.caret(a.checkVal(!0))},0)},clearBuffer:function(a,b){for(var c=this.mask.length,d=a;d<b&&d<c;d++)this.tests[d]&&(this.buffer[d]=this.options.placeholder)},writeBuffer:function(){return this.$element.val(this.buffer.join("")).val()},
checkVal:function(a){for(var b=this.mask.length,c=this.$element.val(),d=-1,e=0,h=0;e<b;e++)if(this.tests[e]){for(this.buffer[e]=this.options.placeholder;h++<c.length;){var g=c.charAt(h-1);if(this.tests[e].test(g)){this.buffer[e]=g;d=e;break}}if(h>c.length)break}else this.buffer[e]==c.charAt(h)&&e!=this.partialPosition&&(h++,d=e);if(!a&&d+1<this.partialPosition)this.$element.val(""),this.clearBuffer(0,b);else if(a||d+1>=this.partialPosition)this.writeBuffer(),a||this.$element.val(this.$element.val().substring(0,
d+1));return this.partialPosition?e:this.firstNonMaskPos}};c.fn.inputmask=function(a){return this.each(function(){var b=c(this);b.data("inputmask")||b.data("inputmask",new g(this,a))})};c.fn.inputmask.defaults={placeholder:"_",definitions:{}};c.fn.inputmask.Constructor=g;c(function(){c("body").on("focus.inputmask.data-api","[data-mask]",function(a){var b=c(this);b.data("inputmask")||(a.preventDefault(),b.inputmask(b.data()))})})}(window.jQuery);
;/**
resources/public/mooncake/plugins/uniform/jquery.uniform.min.js
*/
(function(a){a.uniform={options:{selectClass:"uniform-selector",radioClass:"uniform-radio",checkboxClass:"uniform-checker",fileClass:"uniform-uploader",filenameClass:"uniform-filename",fileBtnClass:"uniform-action",fileDefaultText:"No file selected",fileBtnText:"Choose File",checkedClass:"uniform-checked",focusClass:"uniform-focus",disabledClass:"uniform-disabled",buttonClass:"uniform-button",activeClass:"uniform-active",hoverClass:"uniform-hover",useID:true,idPrefix:"uniform",resetSelector:false,autoHide:true},elements:[]};if(a.browser.msie&&a.browser.version<7){a.support.selectOpacity=false}else{a.support.selectOpacity=true}a.fn.uniform=function(k){k=a.extend(a.uniform.options,k);var d=this;if(k.resetSelector!=false){a(k.resetSelector).mouseup(function(){function l(){a.uniform.update(d)}setTimeout(l,10)})}function j(m){var l=a(m);l.addClass(l.attr("type"));b(m)}function g(l){a(l).addClass("uniform");b(l)}function i(o){var m=a(o);var p=a("<div>"),l=a("<span>");p.addClass(k.buttonClass);if(k.useID&&m.attr("id")){p.attr("id",k.idPrefix+"-"+m.attr("id"))}var n;if(m.is("a")||m.is("button")){n=m.html()}else{if(m.is(":submit")||m.is(":reset")||m.is("input[type=button]")){n=m.attr("value")}}n=n==""?m.is(":reset")?"Reset":"Submit":n;l.html(n);m.css("opacity",0);m.wrap(p);m.wrap(l);p=m.closest("div");l=m.closest("span");if(m.is(":disabled")){p.addClass(k.disabledClass)}p.bind({"mouseenter.uniform":function(){p.addClass(k.hoverClass)},"mouseleave.uniform":function(){p.removeClass(k.hoverClass);p.removeClass(k.activeClass)},"mousedown.uniform touchbegin.uniform":function(){p.addClass(k.activeClass)},"mouseup.uniform touchend.uniform":function(){p.removeClass(k.activeClass)},"click.uniform touchend.uniform":function(r){if(a(r.target).is("span")||a(r.target).is("div")){if(o[0].dispatchEvent){var q=document.createEvent("MouseEvents");q.initEvent("click",true,true);o[0].dispatchEvent(q)}else{o[0].click()}}}});o.bind({"focus.uniform":function(){p.addClass(k.focusClass)},"blur.uniform":function(){p.removeClass(k.focusClass)}});a.uniform.noSelect(p);b(o)}function e(o){var m=a(o);var p=a("<div />"),l=a("<span />");if(!m.css("display")=="none"&&k.autoHide){p.hide()}p.addClass(k.selectClass);if(k.useID&&o.attr("id")){p.attr("id",k.idPrefix+"-"+o.attr("id"))}var n=o.find(":selected:first");if(n.length==0){n=o.find("option:first")}l.html(n.html());o.css("opacity",0);o.wrap(p);o.before(l);p=o.parent("div");l=o.siblings("span");o.bind({"change.uniform":function(){l.html(o.find(":selected").html());p.removeClass(k.activeClass)},"focus.uniform":function(){p.addClass(k.focusClass)},"blur.uniform":function(){p.removeClass(k.focusClass);p.removeClass(k.activeClass)},"mousedown.uniform touchbegin.uniform":function(){p.addClass(k.activeClass)},"mouseup.uniform touchend.uniform":function(){p.removeClass(k.activeClass)},"click.uniform touchend.uniform":function(){p.removeClass(k.activeClass)},"mouseenter.uniform":function(){p.addClass(k.hoverClass)},"mouseleave.uniform":function(){p.removeClass(k.hoverClass);p.removeClass(k.activeClass)},"keyup.uniform":function(){l.html(o.find(":selected").html())}});if(a(o).is(":disabled")){p.addClass(k.disabledClass)}a.uniform.noSelect(l);b(o)}function f(n){var m=a(n);var o=a("<div />"),l=a("<span />");if(!m.css("display")=="none"&&k.autoHide){o.hide()}o.addClass(k.checkboxClass);if(k.useID&&n.attr("id")){o.attr("id",k.idPrefix+"-"+n.attr("id"))}a(n).wrap(o);a(n).wrap(l);l=n.parent();o=l.parent();a(n).css("opacity",0).bind({"focus.uniform":function(){o.addClass(k.focusClass)},"blur.uniform":function(){o.removeClass(k.focusClass)},"click.uniform touchend.uniform":function(){if(!a(n).is(":checked")){l.removeClass(k.checkedClass)}else{l.addClass(k.checkedClass)}},"change.uniform":function(){if(!a(n).is(":checked")){l.removeClass(k.checkedClass)}else{l.addClass(k.checkedClass)}},"mousedown.uniform touchbegin.uniform":function(){o.addClass(k.activeClass)},"mouseup.uniform touchend.uniform":function(){o.removeClass(k.activeClass)},"mouseenter.uniform":function(){o.addClass(k.hoverClass)},"mouseleave.uniform":function(){o.removeClass(k.hoverClass);o.removeClass(k.activeClass)}});if(a(n).is(":checked")){l.addClass(k.checkedClass)}if(a(n).is(":disabled")){o.addClass(k.disabledClass)}b(n)}function c(n){var m=a(n);var o=a("<div />"),l=a("<span />");if(!m.css("display")=="none"&&k.autoHide){o.hide()}o.addClass(k.radioClass);if(k.useID&&n.attr("id")){o.attr("id",k.idPrefix+"-"+n.attr("id"))}a(n).wrap(o);a(n).wrap(l);l=n.parent();o=l.parent();a(n).css("opacity",0).bind({"focus.uniform":function(){o.addClass(k.focusClass)},"blur.uniform":function(){o.removeClass(k.focusClass)},"click.uniform touchend.uniform":function(){if(!a(n).is(":checked")){l.removeClass(k.checkedClass)}else{var p=k.radioClass.split(" ")[0];a("."+p+" span."+k.checkedClass+":has([name='"+a(n).attr("name")+"'])").removeClass(k.checkedClass);l.addClass(k.checkedClass)}},"change.uniform":function(){if(!a(n).is(":checked")){l.removeClass(k.checkedClass)}else{var p=k.radioClass.split(" ")[0];a("."+p+" span."+k.checkedClass+":has([name='"+a(n).attr("name")+"'])").removeClass(k.checkedClass);l.addClass(k.checkedClass)}},"mousedown.uniform touchend.uniform":function(){if(!a(n).is(":disabled")){o.addClass(k.activeClass)}},"mouseup.uniform touchbegin.uniform":function(){o.removeClass(k.activeClass)},"mouseenter.uniform touchend.uniform":function(){o.addClass(k.hoverClass)},"mouseleave.uniform":function(){o.removeClass(k.hoverClass);o.removeClass(k.activeClass)}});if(a(n).is(":checked")){l.addClass(k.checkedClass)}if(a(n).is(":disabled")){o.addClass(k.disabledClass)}b(n)}function h(q){var o=a(q);var r=a("<div />"),p=a("<span>"+k.fileDefaultText+"</span>"),m=a("<span>"+k.fileBtnText+"</span>");if(!o.css("display")=="none"&&k.autoHide){r.hide()}r.addClass(k.fileClass);p.addClass(k.filenameClass);m.addClass(k.fileBtnClass);if(k.useID&&o.attr("id")){r.attr("id",k.idPrefix+"-"+o.attr("id"))}o.wrap(r);o.after(m);o.after(p);r=o.closest("div");p=o.siblings("."+k.filenameClass);m=o.siblings("."+k.fileBtnClass);if(!o.attr("size")){var l=r.width();o.attr("size",l/10)}var n=function(){var s=o.val();if(s===""){s=k.fileDefaultText}else{s=s.split(/[\/\\]+/);s=s[(s.length-1)]}p.text(s)};n();o.css("opacity",0).bind({"focus.uniform":function(){r.addClass(k.focusClass)},"blur.uniform":function(){r.removeClass(k.focusClass)},"mousedown.uniform":function(){if(!a(q).is(":disabled")){r.addClass(k.activeClass)}},"mouseup.uniform":function(){r.removeClass(k.activeClass)},"mouseenter.uniform":function(){r.addClass(k.hoverClass)},"mouseleave.uniform":function(){r.removeClass(k.hoverClass);r.removeClass(k.activeClass)}});if(a.browser.msie){o.bind("click.uniform.ie7",function(){setTimeout(n,0)})}else{o.bind("change.uniform",n)}if(o.is(":disabled")){r.addClass(k.disabledClass)}a.uniform.noSelect(p);a.uniform.noSelect(m);b(q)}a.uniform.restore=function(l){if(l==undefined){l=a(a.uniform.elements)}a(l).each(function(){if(a(this).is(":checkbox")){a(this).unwrap().unwrap()}else{if(a(this).is("select")){a(this).siblings("span").remove();a(this).unwrap()}else{if(a(this).is(":radio")){a(this).unwrap().unwrap()}else{if(a(this).is(":file")){a(this).siblings("span").remove();a(this).unwrap()}else{if(a(this).is("button, :submit, :reset, a, input[type='button']")){a(this).unwrap().unwrap()}}}}}a(this).unbind(".uniform");a(this).css("opacity","1");var m=a.inArray(a(l),a.uniform.elements);a.uniform.elements.splice(m,1)})};function b(l){l=a(l).get();if(l.length>1){a.each(l,function(m,n){a.uniform.elements.push(n)})}else{a.uniform.elements.push(l)}}a.uniform.noSelect=function(l){function m(){return false}a(l).each(function(){this.onselectstart=this.ondragstart=m;a(this).mousedown(m).css({MozUserSelect:"none"})})};a.uniform.update=function(l){if(l==undefined){l=a(a.uniform.elements)}l=a(l);l.each(function(){var n=a(this);if(n.is("select")){var m=n.siblings("span");var p=n.parent("div");p.removeClass(k.hoverClass+" "+k.focusClass+" "+k.activeClass);m.html(n.find(":selected").html());if(n.is(":disabled")){p.addClass(k.disabledClass)}else{p.removeClass(k.disabledClass)}}else{if(n.is(":checkbox")){var m=n.closest("span");var p=n.closest("div");p.removeClass(k.hoverClass+" "+k.focusClass+" "+k.activeClass);m.removeClass(k.checkedClass);if(n.is(":checked")){m.addClass(k.checkedClass)}if(n.is(":disabled")){p.addClass(k.disabledClass)}else{p.removeClass(k.disabledClass)}}else{if(n.is(":radio")){var m=n.closest("span");var p=n.closest("div");p.removeClass(k.hoverClass+" "+k.focusClass+" "+k.activeClass);m.removeClass(k.checkedClass);if(n.is(":checked")){m.addClass(k.checkedClass)}if(n.is(":disabled")){p.addClass(k.disabledClass)}else{p.removeClass(k.disabledClass)}}else{if(n.is(":file")){var p=n.parent("div");var o=n.siblings("."+k.filenameClass);btnTag=n.siblings(k.fileBtnClass);p.removeClass(k.hoverClass+" "+k.focusClass+" "+k.activeClass);o.text(n.val());if(n.is(":disabled")){p.addClass(k.disabledClass)}else{p.removeClass(k.disabledClass)}}else{if(n.is(":submit")||n.is(":reset")||n.is("button")||n.is("a")||l.is("input[type=button]")){var p=n.closest("div");p.removeClass(k.hoverClass+" "+k.focusClass+" "+k.activeClass);if(n.is(":disabled")){p.addClass(k.disabledClass)}else{p.removeClass(k.disabledClass)}}}}}}})};return this.each(function(){if(a.support.selectOpacity){var l=a(this);if(l.is("select")){if(!this.multiple){if(l.attr("size")==undefined||l.attr("size")<=1){e(l)}}}else{if(l.is(":checkbox")){f(l)}else{if(l.is(":radio")){c(l)}else{if(l.is(":file")){h(l)}else{if(l.is(":text, :password, input[type='email']")){j(l)}else{if(l.is("textarea")){g(l)}else{if(l.is("a")||l.is(":submit")||l.is(":reset")||l.is("button")||l.is("input[type=button]")){i(l)}}}}}}}}})}})(jQuery);
;/**
resources/public/mooncake/assets/js/template.js
*/
/*
 * MoonCake v1.3.1 - Template JS
 *
 * This file is part of MoonCake, an Admin template build for sale at ThemeForest.
 * For questions, suggestions or support request, please mail me at maimairel@yahoo.com
 *
 * Development Started:
 * July 28, 2012
 * Last Update:
 * December 07, 2012
 *
 */

;(function( $, window, document, undefined ) {
	
	var MoonCake = function( document ) {
		this.document = $(document);
	}

	MoonCake.prototype = {
		
		version: '1.0', 

		defaults: {
			showSidebarToggleButton: true, 
			fixedSidebar: false
		}, 

		init: function( options ) {

			this.options = $.extend({}, this.defaults, options);

			this.bindEventHandlers();

			this.updateSidebarNav( $( '#sidebar #navigation > ul > li.active' ).first(), true );

			this.options.showSidebarToggleButton && this.attachSidebarToggleButton();
			this.options.fixedSidebar && $.fn.affix && $('#sidebar').affix({
				offset: {
					top: 0
				}
			});
			
			return this;
		}, 

		ready: function( fn ) {
			this.document.ready($.proxy(function() {
				fn.call( this.document, this );
			}, this));

			return this;
		}, 

		attachSidebarToggleButton: function() {

			var toggleButton = $( '<li id="sidebar-toggle-wrap"><span id="sidebar-toggle"><span></span></span></li>' );

			toggleButton
				.appendTo( '#wrapper #sidebar #navigation > ul' )
				.children( '#sidebar-toggle' )
				.on( 'click.template', function(e) {
					if( !!$( '#sidebar #navigation > ul > li.active:first .inner-nav' ).length ) {
						$(this).parents( '#content' )
							.toggleClass( 'sidebar-minimized' )
						.end()
							.toggleClass( 'toggled' );
					}
					e.preventDefault();
				})
				.toggleClass( 'disabled', !$( '#sidebar #navigation > ul > li.active:first .inner-nav' ).length )
				.toggleClass( 'toggled', $( '#wrapper #content' ).hasClass( 'sidebar-minimized' ) );
		}, 

		bindEventHandlers: function() {

			// Search and Dropdown-menu inputs
			$( '#header #header-search .search-query')
				.add( $( '.dropdown-menu' )
				.find( ':input' ) )
				.on( 'click.template', function( e ) {
					e.stopPropagation();
				});

			var self = this;
			// Sidebar Navigation
			$( '#sidebar #navigation > ul > li' )
				.filter(':not(#sidebar-toggle-wrap)')
				.on( 'click.template', ' > a, > span', function( e ) {
					if( $(this).is('a') && undefined !== $(this).attr('href') )
						return;

					self.updateSidebarNav( $(this).parent() );
					e.stopPropagation();
				});

			// Collapsible Boxes
			$( '.widget .widget-header [data-toggle=widget]' )
			.each(function(i, element) {
				var p = $( this ).parents( '.widget' );
				if( !p.children( '.widget-inner-wrap' ).length ) {
					p.children( ':not(.widget-header)' )
						.wrapAll( $('<div></div>').addClass( 'widget-inner-wrap' ) );
				}
			}).on( 'click', function(e) {
				var p = $( this ).parents( '.widget' );
				if( p.hasClass('collapsed') ) {
					p.removeClass( 'collapsed' )
						.children( '.widget-inner-wrap' ).hide().slideDown( 250 );
				} else {
					p.children( '.widget-inner-wrap' ).slideUp( 250, function() {
						p.addClass( 'collapsed' );
					});
				}
				e.preventDefault();
			});
		}, 

		updateSidebarNav: function( nav, init ) {
			var hasInnerNav = !!nav.children( '.inner-nav' ).length;
			nav
				.siblings().removeClass( 'active open' )
			.end()
				.addClass( 'active' ).toggleClass( 'open' );

			!init && $( '#content' )
				.toggleClass( 'sidebar-minimized', !hasInnerNav );

			$( '#sidebar-toggle' )
				.toggleClass( 'disabled', !hasInnerNav )
				.toggleClass( 'toggled', $( '#content' ).hasClass( 'sidebar-minimized' ) );

			nav = nav.children( '.inner-nav' ).get(0);
			$( '#wrapper #sidebar #navigation > ul' )
				.css( 'minHeight', nav? $( nav ).outerHeight() : '');
		}
	};

	$.template = new MoonCake( document ).ready( function( template ) {

		template.init( $('body').data() );

	});

	
}) (jQuery, window, document);
;/**
resources/public/mooncake/assets/js/setup.js
*/
/*
 * MoonCake v1.3.1 - Template Setup JS
 *
 * This file is part of MoonCake, an Admin template build for sale at ThemeForest.
 * For questions, suggestions or support request, please mail me at maimairel@yahoo.com
 *
 * Development Started:
 * July 28, 2012
 * Last Update:
 * December 07, 2012
 *
 */

;(function( $, window, document, undefined ) {
	
	
	$( document ).ready( function( e ) {
								  
		// Restrict any other content beside numbers
		$(":input[data-accept=numbers]").keydown(function(event) {
											
			// Allow: backspace, delete, tab, escape, and enter
			if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
				 // Allow: Ctrl+A
				(event.keyCode == 65 && event.ctrlKey === true) || 
				 // Allow: home, end, left, right
				(event.keyCode >= 35 && event.keyCode <= 39)) {
					 // let it happen, don't do anything
					 return;
			}
			else {
				// Ensure that it is a number and stop the keypress
				if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
					event.preventDefault(); 
				}   
			}
		});
		
		// Initialize Bootstrap Tooltips
		$.fn.tooltip && $('[rel="tooltip"]').tooltip();
		
		// Initialize Bootstrap Popovers
		$.fn.popover && $('[rel="popover"]').popover();

		// Style checkboxes and radios
		$.fn.uniform && $(':radio.uniform, :checkbox.uniform').uniform();

		// IE Placeholder
		$.fn.placeholder && $('[placeholder]').placeholder();

		// Checkable Tables
		$( '.table-checkable thead th.checkbox-column :checkbox' ).on('change', function() {
			var checked = $( this ).prop( 'checked' );
			$( this ).parents('table').children('tbody').each(function(i, tbody) {
				$(tbody).find('.checkbox-column').each(function(j, cb) {
					$( ':checkbox', $(cb) ).prop( "checked", checked ).trigger('change');
					$(cb).closest('tr').toggleClass( 'checked', checked );
				});
			});
		});
		$( '.table-checkable tbody tr td.checkbox-column :checkbox' ).on('change', function() {
			var checked = $( this ).prop( 'checked' );
			$( this ).closest('tr').toggleClass( 'checked', checked );
		});

		// Task List Status
		var toggleTaskListCheckbox = function(cb, checked) { $(cb).closest('li').toggleClass( 'done', checked ); }
		$( '.task-list' ).each(function(i, tl) {
			$( '.checkbox', $(tl) ).each(function() {
				var cb = $( ':checkbox', $(this) ), 
					checked = $(cb).prop('checked');
				toggleTaskListCheckbox(cb, checked);
				$(cb).on('change', function() {
					toggleTaskListCheckbox(this, $(this).prop('checked'));
				});	
			});
		});

		// Bootstrap Dropdown Workaround for touch devices
		$(document).on('touchstart.dropdown.data-api', '.dropdown-menu', function (e) { e.stopPropagation() });
				
		// Extend jQuery Validate Defaults.
		// You mav remove this if you use an another validation library
		if( $.validator ) {
			$.extend( $.validator.defaults, {
				errorClass: "error",
				validClass: "success", 
				highlight: function(element, errorClass, validClass) {
					if (element.type === 'radio') {
						this.findByName(element.name).addClass(errorClass).removeClass(validClass);
					} else {
						$(element).addClass(errorClass).removeClass(validClass);
					}
					$(element).closest(".control-group").addClass(errorClass).removeClass(validClass);
				},
				unhighlight: function(element, errorClass, validClass) {
					if (element.type === 'radio') {
						this.findByName(element.name).removeClass(errorClass).addClass(validClass);
					} else {
						$(element).removeClass(errorClass).addClass(validClass);
					}
					$(element).closest(".control-group").removeClass(errorClass).addClass(validClass);
				}
			});
			
			var _base_resetForm = $.validator.prototype.resetForm;
			$.extend( $.validator.prototype, {
				resetForm: function() {
					_base_resetForm.call( this );
					this.elements().closest('.control-group')
						.removeClass(this.settings.errorClass + ' ' + this.settings.validClass);
				}, 
				showLabel: function(element, message) {
					var label = this.errorsFor( element );
					if ( label.length ) {
						// refresh error/success class
						label.removeClass( this.settings.validClass ).addClass( this.settings.errorClass );

						// check if we have a generated label, replace the message then
						if ( label.attr("generated") ) {
							label.html(message);
						}
					} else {
						// create label
						label = $("<" + this.settings.errorElement + "/>")
							.attr({"for":  this.idOrName(element), generated: true})
							.addClass(this.settings.errorClass)
							.addClass('help-block')
							.html(message || "");
						if ( this.settings.wrapper ) {
							// make sure the element is visible, even in IE
							// actually showing the wrapped element is handled elsewhere
							label = label.hide().show().wrap("<" + this.settings.wrapper + "/>").parent();
						}
						if ( !this.labelContainer.append(label).length ) {
							if ( this.settings.errorPlacement ) {
								this.settings.errorPlacement(label, $(element) );
							} else {
							label.insertAfter(element);
							}
						}
					}
					if ( !message && this.settings.success ) {
						label.text("");
						if ( typeof this.settings.success === "string" ) {
							label.addClass( this.settings.success );
						} else {
							this.settings.success( label, element );
						}
					}
					this.toShow = this.toShow.add(label);
				}
			});
		}
	});
	
}) (jQuery, window, document);
;/**
resources/public/mooncake/assets/js/customizer.js
*/
/*
 * MoonCake v1.3.1 - Template JS
 *
 * This file is part of MoonCake, an Admin template build for sale at ThemeForest.
 * For questions, suggestions or support request, please mail me at maimairel@yahoo.com
 *
 * Development Started:
 * July 28, 2012
 * Last Update:
 * December 07, 2012
 *
 */

;(function( $, window, document, undefined ) {
	
	var Customizer = function( ) {
		this.init();
	}

	Customizer.prototype = {

		patterns: [
			'assets/images/layout/bg/arches.png', 
			'assets/images/layout/bg/blu_stripes.png', 
			'assets/images/layout/bg/bright_squares.png', 
			'assets/images/layout/bg/brushed_alu.png', 
			'assets/images/layout/bg/circles.png', 
			'assets/images/layout/bg/climpek.png', 
			'assets/images/layout/bg/connect.png', 
			'assets/images/layout/bg/corrugation.png', 
			'assets/images/layout/bg/cubes.png', 
			'assets/images/layout/bg/diagonal-noise.png', 
			'assets/images/layout/bg/diagonal_striped_brick.png', 
			'assets/images/layout/bg/diamonds.png', 
			'assets/images/layout/bg/diamond_upholstery.png', 
			'assets/images/layout/bg/escheresque.png', 
			'assets/images/layout/bg/fabric_plaid.png', 
			'assets/images/layout/bg/furley_bg.png', 
			'assets/images/layout/bg/gplaypattern.png', 
			'assets/images/layout/bg/gradient_squares.png', 
			'assets/images/layout/bg/grey.png', 
			'assets/images/layout/bg/grilled.png', 
			'assets/images/layout/bg/hexellence.png', 
			'assets/images/layout/bg/lghtmesh.png', 
			'assets/images/layout/bg/light_alu.png', 
			'assets/images/layout/bg/light_checkered_tiles.png', 
			'assets/images/layout/bg/light_honeycomb.png', 
			'assets/images/layout/bg/littleknobs.png', 
			'assets/images/layout/bg/nistri.png', 
			'assets/images/layout/bg/noise_lines.png', 
			'assets/images/layout/bg/noise_pattern_with_crosslines.png', 
			'assets/images/layout/bg/noisy_grid.png', 
			'assets/images/layout/bg/norwegian_rose.png', 
			'assets/images/layout/bg/pineapplecut.png', 
			'assets/images/layout/bg/pinstripe.png', 
			'assets/images/layout/bg/project_papper.png', 
			'assets/images/layout/bg/ravenna.png', 
			'assets/images/layout/bg/reticular_tissue.png', 
			'assets/images/layout/bg/retina_wood.png', 
			'assets/images/layout/bg/rockywall.png', 
			'assets/images/layout/bg/roughcloth.png', 
			'assets/images/layout/bg/shattered.png', 
			'assets/images/layout/bg/silver_scales.png', 
			'assets/images/layout/bg/skelatal_weave.png', 
			'assets/images/layout/bg/small-crackle-bright.png', 
			'assets/images/layout/bg/small_tiles.png', 
			'assets/images/layout/bg/square_bg.png', 
			'assets/images/layout/bg/struckaxiom.png', 
			'assets/images/layout/bg/subtle_stripes.png', 
			'assets/images/layout/bg/vichy.png', 
			'assets/images/layout/bg/washi.png', 
			'assets/images/layout/bg/wavecut.png', 
			'assets/images/layout/bg/weave.png', 
			'assets/images/layout/bg/whitey.png', 
			'assets/images/layout/bg/wood_pattern.png', 
			'assets/images/layout/bg/white_brick_wall.png', 
			'assets/images/layout/bg/white_tiles.png', 
			'assets/images/layout/bg/worn_dots.png'
		], 
		invalidPatterns: [], 

		init: function( ) {
			var customizer = $( '#customizer', 'body' ), self = this;

			if( customizer && customizer.length ) {

				// Generate Pattern List
				var patternList = $( '<div id="pattern-list" class="clearfix"><ul></ul></div>' ).appendTo( customizer );

				customizer.find( '#showButton' ).on( 'click', $.proxy(function(e) { 
					customizer.animate({ left: customizer.css('left') === '0px'? '-236px' : 0 }, 'slow');

					if( !patternList.hasClass( 'patterns-loaded' ) ) {
						this.preloadPatterns(function() {
							$.each(this.patterns, function(i, p) {
								if( $.inArray( p, self.invalidPatterns) == -1 ) {
									$( 'ul', patternList).append( $('<li></li>').css( 'backgroundImage', 'url(' + p + ')' ) );
								}
							});
							patternList
								.addClass( 'patterns-loaded' )
								.on( 'click', 'li', function() {
									$('body').css( 'backgroundImage', $(this).css( 'backgroundImage' ) );
								});
						});
					}
				}, this));

				// Init Layout Changer
				customizer.find( 'input:checkbox[name="layout-mode"]' )
					.on( 'change', function() {
						$( '#wrapper' ).toggleClass( 'full', !$(this).attr('checked') );
					})
					.prop('checked', !$('#wrapper').hasClass('full'))
					.trigger('change');
			}
		}, 

		preloadPatterns: function( callback ) {
			var toLoad = this.patterns.length, 
				self = this;
			$.each(this.patterns, function(i, p) {
				var img = $( '<img />' ).load( function() {
					toLoad--;
					toLoad <= 0 && callback.call( self );
				}).error(function() {
					toLoad--;
					self.invalidPatterns.push(p);
				});

				img[0].src = p;
			});
		}
	};

	$( document ).ready( function( ) {
		new Customizer();
	});

	
}) (jQuery, window, document);