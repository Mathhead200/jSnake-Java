<?php

if( $_GET["password"] != "jSnake_MHC8" ) {
	die();
}

class Score {
	public $name;
	public $value;
	
	public function __construct($str, $num) {
		if( is_null($num) ) {
			$arr = explode(" ", $str, 2);
			$this->name = $arr[0];
			$this->value = $arr[1];
		} else {
			$this->name = $str;
			$this->value = $num;
		}	
	}
	
	public function getLine() {
		return $this->name . " " . $this->value;
	}
}

$scores = array();
$lines = file("scores.txt");

foreach( $lines as $line ) {
	array_push( $scores, new Score($line) );
}

$myScore = new Score( $_GET["name"], $_GET["score"] );

$numScores = count($scores);
for( $i = 0; $i < $numScores; $i++ ) {
	$score = $scores[$i];
	echo $myScore->value . " > " . $score->value;
	if( $myScore->value > $score->value ) {
		array_splice( $scores, $i, 0, array($myScore) );
		echo $i + 1;
		break;
	} else if( $i == $numScores - 1 ) {
		array_push( $scores, $myScore );
		echo $munScores + 1;
		break;
	}
}

$outStr = "";
foreach( $scores as $score ) {
	$outStr = $outStr . $score->getLine() . "\n";
}

echo "<br />\n$outStr";

if( !file_put_contents( "scores.txt", $outStr ) ) {
	echo "error:dnw";
}

?>