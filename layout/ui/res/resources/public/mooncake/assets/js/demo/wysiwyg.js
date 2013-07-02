/*
 * MoonCake v1.3.1 - WYSIWYG Demo JS
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

	$(document).ready(function() {
		
		// When all page resources has finished loading
		
		if($.fn.cleditor) {
			
			$( '#cleditor').cleditor({ width: '100%' });
		}
	});
	
}) (jQuery, window, document);