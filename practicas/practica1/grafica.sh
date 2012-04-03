#!/bin/bash

min=$1
step=$2
max=$3
plot=$4
host=$5
puerto=$6

if [ $# -eq 0 ]; then
echo -e  "uso:\n\t $0 min step max plot host port"
exit
fi

dest=`mktemp -p .`

for i in `seq $min $step $max`
do
echo -ne "$i\t" >> $dest
echo -ne "`java cliente l $i |tr -d '\n'`\t" >> $dest
echo -ne "`java cliente u $i $host $puerto|tr -d '\n'`\t" >> $dest
echo -e "`java cliente t $i $host $puerto|tr -d '\n'`" >> $dest

echo $i
done 
echo "fin de la obtención de datos"

file="plot.$(date +%s)"
mv $dest $file

if [ "x${plot}x" = "xsix"  ] ; then
echo "
set terminal x11 size 1024,768 enhanced font '/usr/share/fonts/liberation/LiberationSans-Regular.ttf' 12
#set output '${file}.png'
set title 'Tiempos de respuesta';
set xlabel 'n de accesos';
set ylabel 'ns';
#set log y;
plot \"${file}\" using 1:2 title 'local' w lines,\
\"\" using 1:3 title 'udp' w lines,\
\"\" using 1:4 title 'tcp' w lines
"|gnuplot -persist
fi
cat ${file}

if [ -f ${dest} ];
then
	rm ${dest}
fi
