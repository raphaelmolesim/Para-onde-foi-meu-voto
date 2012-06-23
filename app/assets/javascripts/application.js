// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, vendor/assets/javascripts,
// or vendor/assets/javascripts of plugins, if any, can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// the compiled file.
//
// WARNING: THE FIRST BLANK LINE MARKS THE END OF WHAT'S TO BE PROCESSED, ANY BLANK LINE SHOULD
// GO AFTER THE REQUIRES BELOW.
//
//= require jquery
//= require jquery_ujs
//= require_tree .
$().ready(function(){
$('#select-input').change(function(){
    base = '/vereador/'
    $(location).attr('href',base + this.value)

})
$('#star').raty(
{
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

}

    )})
/*
 window.fbAsyncInit = function() {
    FB.init({
      appId      : 'YOUR_APP_ID', // App ID
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
   */  
