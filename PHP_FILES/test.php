<?php

$date = date('d/m/Y h.i.s A', strtotime('2011-08-27 18:29:31'));
echo $date . "\n";

$date = date('d/m/Y', strtotime('2011-08-27 18:29:31'));
echo $date . "\n";
$time = date('h:i', strtotime('2011-08-27 18:29:31'));
echo $time . "\n";
?>
