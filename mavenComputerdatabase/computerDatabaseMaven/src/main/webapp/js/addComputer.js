$(document).ready(function() {
	
	$('#addCompForm').submit(function(e){
		let name = $('#computerName').val()
		let introduced = $('#introduced').val()
		let discontinued = $('#discontinued').val()
		let dateIntroduced = Date.parse(introduced);
		let dateDiscontinued = Date.parse(discontinued);
		let isOk = true;
		
		$(".error").remove();
		
		if(name.length < 1){
			isOk = false;
			$('#computerName').after('<span class="error">This field is required</span>');
		}
		
		if(dateDiscontinued > 0){
			if(dateDiscontinued < dateIntroduced){
				isOk = false;
				$('#discontinued').after('<span class="error">This field should be older than introduced</span>');
			}
		}
		
		if(!isOk){
			e.preventDefault();
		}
	})
})
