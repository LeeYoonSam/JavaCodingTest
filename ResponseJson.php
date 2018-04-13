
// PHP로 JSON 응답하기
<?php
	$p1 = $_GET[param1];
	$p2 = $_GET[param2];

	if($p1 == null || $p2 == null) {
		$response[0] = array(
        'error'=> true,
    );

		echo json_encode($response);
	} else if($p1 == $p2) {
		$response[0] = array(
        'error'=> false,
				'result' => true
    );

		echo json_encode($response);
	} else {
				$response[0] = array(
        'error'=> false,
				'result' => false
    );

		echo json_encode($response);
	}

?>