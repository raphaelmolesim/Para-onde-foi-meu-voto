// = require libs/jit-yc.js
// = require libs/jquery-1.8.0.min.js
// = require libs/jquery-ui-1.8.23.custom.min.js
// = require libs/jquery.raty.min.js
// = require libs/tree-map.js
// = require libs/jquery.bar.js
// = require libs/fb-auth.js
// = require warning.js
// = require profile.js.coffee
// = require bar_chart.js

$().ready(function(){
  $('#star').raty({
      size: 40,
      starOff   : 'star-off-big.png',
      starOn    : 'star-on-big.png',
      click: function(score){
          base = '/votar/'
         // $(location).attr('href', base + score)
          $('input[name=vereador]').val()
          $('input[name=fator]').val(score)

          // alert($('input[name=vereador]').val())

          $('#avaliacao').submit()
          // $.post(base + score)
      }
  })
});

window.fbAsyncInit = function() {
  FB.init({
    appId      : '398241096888777', // App ID
    channelUrl : '//para-onde-foi-meu-voto.herokuapp.com//channel.html', // Channel File
    status     : true, // check login status
    cookie     : true, // enable cookies to allow the server to access the session
    xfbml      : true  // parse XFBML
  });

  // Additional initialization code here
};

// Load the SDK Asynchronously
(function(d){
   var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement('script'); js.id = id; js.async = true;
   js.src = "//connect.facebook.net/en_US/all.js";
   ref.parentNode.insertBefore(js, ref);
 }(document));  

