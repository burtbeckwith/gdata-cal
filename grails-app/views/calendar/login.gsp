<head>
<meta name="layout" content="calendar" />
<title>Google Credentials</title>
<style type='text/css' media='screen'>
#login {
	margin:15px 0px; padding:0px;
	text-align:center;
}
#login .inner {
	width: 300px;
	margin: 0px auto;
	text-align: left;
	padding: 10px;
	border-top: 1px solid #499ede;
	border-bottom: 1px solid #499ede;
	background-color: #EEF;
}
#login .inner .fheader {
	padding:4px;margin:3px 0px 3px 0;color:#2e3741;font-size:14px;font-weight:bold;
}
#login .inner .cssform p {
	clear: left;
	margin: 0;
	padding: 5px 0 8px 0;
	padding-left: 105px;
	border-top: 1px solid gray;
	margin-bottom: 10px;
	height: 1%;
}
#login .inner .cssform input[type='text'] {
	width: 120px;
}
#login .inner .cssform label {
	font-weight: bold;
	float: left;
	margin-left: -105px;
	width: 100px;
}
#login .inner .login_message {color:red;}
#login .inner .text_ {width:120px;}
#login .inner .chk {height:12px;}
</style>
</head>

<body>
	<div id='login'>
		<div class='inner'>
			<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
			</g:if>
			<div class='fheader'>Please enter your Google credentials</div>
			<g:form action='auth' name='loginForm' class='cssform' autocomplete='off'>
				<p>
					<label for='username'>Email</label>
					<g:textField name='username' />
				</p>
				<p>
					<label for='password'>Password</label>
					<g:passwordField name='password' class='text_'/>
				</p>
				<p><input type='submit' value='Login' /></p>
			</g:form>
		</div>
	</div>
<script type='text/javascript'>
<!--
(function(){
	document.forms['loginForm'].elements['username'].focus();
})();
// -->
</script>
</body>
