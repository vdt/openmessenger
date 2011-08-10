jQuery(document).ready(function($) {
  $('#menus').accordion({'header': '.accordion_title'});
  $('#sets').accordion({
    'header': '.accordion_title',
    'active': false,
    'collapsible': true
  });
});
