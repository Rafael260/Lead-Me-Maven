#!/bin/bash
head -n 1 matricula-componente-2011.1.csv > matriculaOK-componente-2011.1.csv;
cat matricula-componente-2011.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2011.1.csv;
head -n 1 matricula-componente-2011.2.csv > matriculaOK-componente-2011.2.csv;
cat matricula-componente-2011.2.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2011.2.csv;
head -n 1 matricula-componente-2012.1.csv > matriculaOK-componente-2012.1.csv;
cat matricula-componente-2012.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2012.1.csv;
head -n 1 matricula-componente-2012.2.csv > matriculaOK-componente-2012.2.csv;
cat matricula-componente-2012.2.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2012.2.csv;
head -n 1 matricula-componente-2013.1.csv > matriculaOK-componente-2013.1.csv;
cat matricula-componente-2013.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2013.1.csv;
head -n 1 matricula-componente-2013.2.csv > matriculaOK-componente-2013.2.csv;
cat matricula-componente-2013.2.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2013.2.csv;
head -n 1 matricula-componente-2014.1.csv > matriculaOK-componente-2014.1.csv;
cat matricula-componente-2014.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2014.1.csv;
head -n 1 matricula-componente-2014.2.csv > matriculaOK-componente-2014.2.csv;
cat matricula-componente-2014.2.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2014.2.csv;
head -n 1 matricula-componente-2015.1.csv > matriculaOK-componente-2015.1.csv;
cat matricula-componente-2015.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2015.1.csv;
head -n 1 matricula-componente-2015.2.csv > matriculaOK-componente-2015.2.csv;
cat matricula-componente-2015.2.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2015.2.csv;
head -n 1 matricula-componente-2016.1.csv > matriculaOK-componente-2016.1.csv;
cat matricula-componente-2016.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2016.1.csv;
head -n 1 matricula-componente-2016.2.csv > matriculaOK-componente-2016.2.csv;
cat matricula-componente-2016.2.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2016.2.csv;
head -n 1 matricula-componente-2017.1.csv > matriculaOK-componente-2017.1.csv;
cat matricula-componente-2017.1.csv | grep 92127264 | sort | uniq -u >> matriculaOK-componente-2017.1.csv;
mkdir OK
mv matriculaOK* ./OK;
 
