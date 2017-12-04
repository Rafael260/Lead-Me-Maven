#!/bin/bash

#Separa as matrículas------------------------------------------------------------------------------------------------------------------------
head -n 1 ./matriculas/matricula-componente-2011.1.csv > ./matriculas/matriculaOK-componente-2011.1.csv;
grep 92127264 ./matriculas/matricula-componente-2011.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2011.1.csv;
grep 92127264 ./matriculas/matricula-componente-2011.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2011.1.csv;
head -n 1 ./matriculas/matricula-componente-2011.2.csv > ./matriculas/matriculaOK-componente-2011.2.csv;
grep 92127264 ./matriculas/matricula-componente-2011.2.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2011.2.csv;
grep 92127264 ./matriculas/matricula-componente-2011.2.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2011.2.csv;
head -n 1 ./matriculas/matricula-componente-2012.1.csv > ./matriculas/matriculaOK-componente-2012.1.csv;
grep 92127264 ./matriculas/matricula-componente-2012.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2012.1.csv;
grep 92127264 ./matriculas/matricula-componente-2012.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2012.1.csv;
head -n 1 ./matriculas/matricula-componente-2012.2.csv > ./matriculas/matriculaOK-componente-2012.2.csv;
grep 92127264 ./matriculas/matricula-componente-2012.2.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2012.2.csv;
grep 92127264 ./matriculas/matricula-componente-2012.2.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2012.2.csv;
head -n 1 ./matriculas/matricula-componente-2013.1.csv > ./matriculas/matriculaOK-componente-2013.1.csv;
grep 92127264 ./matriculas/matricula-componente-2013.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2013.1.csv;
grep 92127264 ./matriculas/matricula-componente-2013.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2013.1.csv;
head -n 1 ./matriculas/matricula-componente-2013.2.csv > ./matriculas/matriculaOK-componente-2013.2.csv;
grep 92127264 ./matriculas/matricula-componente-2013.2.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2013.2.csv;
grep 92127264 ./matriculas/matricula-componente-2013.2.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2013.2.csv;
head -n 1 ./matriculas/matricula-componente-2014.1.csv > ./matriculas/matriculaOK-componente-2014.1.csv;
grep 92127264 ./matriculas/matricula-componente-2014.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2014.1.csv;
grep 92127264 ./matriculas/matricula-componente-2014.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2014.1.csv;
head -n 1 ./matriculas/matricula-componente-2014.2.csv > ./matriculas/matriculaOK-componente-2014.2.csv;
grep 92127264 ./matriculas/matricula-componente-2014.2.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2014.2.csv;
grep 92127264 ./matriculas/matricula-componente-2014.2.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2014.2.csv;
head -n 1 ./matriculas/matricula-componente-2015.1.csv > ./matriculas/matriculaOK-componente-2015.1.csv;
grep 92127264 ./matriculas/matricula-componente-2015.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2015.1.csv;
grep 92127264 ./matriculas/matricula-componente-2015.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2015.1.csv;
head -n 1 ./matriculas/matricula-componente-2015.2.csv > ./matriculas/matriculaOK-componente-2015.2.csv;
grep 92127264 ./matriculas/matricula-componente-2015.2.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2015.2.csv;
grep 92127264 ./matriculas/matricula-componente-2015.2.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2015.2.csv;
head -n 1 ./matriculas/matricula-componente-2016.1.csv > ./matriculas/matriculaOK-componente-2016.1.csv;
grep 92127264 ./matriculas/matricula-componente-2016.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2016.1.csv;
grep 92127264 ./matriculas/matricula-componente-2016.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2016.1.csv;
head -n 1 ./matriculas/matricula-componente-2016.2.csv > ./matriculas/matriculaOK-componente-2016.2.csv;
grep 92127264 ./matriculas/matricula-componente-2016.2.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2016.2.csv;
grep 92127264 ./matriculas/matricula-componente-2016.2.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2016.2.csv;
head -n 1 ./matriculas/matricula-componente-2017.1.csv > ./matriculas/matriculaOK-componente-2017.1.csv;
grep 92127264 ./matriculas/matricula-componente-2017.1.csv  | sort | uniq -u >> ./matriculas/matriculaOK-componente-2017.1.csv;
grep 92127264 ./matriculas/matricula-componente-2017.1.csv  | sort | uniq -d >> ./matriculas/matriculaOK-componente-2017.1.csv;
mkdir ./matriculas/OK;
mv ./matriculas/matriculaOK* ./matriculas/OK;
#--------------------------------------------------------------------------------------------------------------------------------------------

#Separa as estruturas curriculares de TI-----------------------------------------------------------------------------------------------------
head -n 1 estruturas-curriculares.csv > estruturas-curricularesOK.csv;
grep -i "tecnologia da informação" estruturas-curriculares.csv >> estruturas-curricularesOK.csv;
mkdir estruturas-curricularesOK;
mv estruturas-curricularesOK.csv ./estruturas-curricularesOK;
#--------------------------------------------------------------------------------------------------------------------------------------------

#Separa apenas os IDs das estruturas curriculares de TI --------------------------------------------------------------------------------------
cat ./estruturas-curricularesOK/estruturas-curricularesOK.csv | cut -d";" -f1 | sort | uniq -d > ./estruturas-curricularesOK/idEstruturaTI2.csv;
cat ./estruturas-curricularesOK/estruturas-curricularesOK.csv | cut -d";" -f1 | sort | uniq -u >> ./estruturas-curricularesOK/idEstruturaTI2.csv;
cat ./estruturas-curricularesOK/idEstruturaTI2.csv | sort -r > ./estruturas-curricularesOK/idEstruturaTI.csv;
rm ./estruturas-curricularesOK/idEstruturaTI2.csv;
#--------------------------------------------------------------------------------------------------------------------------------------------


#Separa curriculo componente de TI-----------------------------------------------------------------------------------------------------------
head -n 1 curriculo-componente-graduacao.csv > curriculo-componente-graduacaoOK.csv;
while read linha
do
grep $linha curriculo-componente-graduacao.csv >> curriculo-componente-graduacaoOK.csv;
done < ./estruturas-curricularesOK/idEstruturaTI.csv;
mkdir curriculo-componente-graduacaoOK;
mv curriculo-componente-graduacaoOK.csv ./curriculo-componente-graduacaoOK;
#--------------------------------------------------------------------------------------------------------------------------------------------


#Separa apenas os IDs dos Componentes de TI--------------------------------------------------------------------------------------------------
cat ./curriculo-componente-graduacaoOK/curriculo-componente-graduacaoOK.csv | cut -d";" -f3 | sort | uniq -d > ./curriculo-componente-graduacaoOK/idComponenteTI2.csv;
cat ./curriculo-componente-graduacaoOK/curriculo-componente-graduacaoOK.csv | cut -d";" -f3 | sort | uniq -u >> ./curriculo-componente-graduacaoOK/idComponenteTI2.csv;
cat ./curriculo-componente-graduacaoOK/idComponenteTI2.csv | sort -r > ./curriculo-componente-graduacaoOK/idComponenteTI.csv;
rm ./curriculo-componente-graduacaoOK/idComponenteTI2.csv;
#--------------------------------------------------------------------------------------------------------------------------------------------

#Separa as turmas------------------------------------------------------------------------------------------------------------------------
head -n 1 ./turmas/turmas-2011.1.csv > ./turmas/turmasOK-2011.1.csv;
while read linha
do
grep linha ./turmas/turmas-2011.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2011.1.csv;
grep linha ./turmas/turmas-2011.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2011.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2011.2.csv > ./turmas/turmasOK-2011.2.csv;
while read linha
do
grep linha ./turmas/turmas-2011.2.csv  | sort | uniq -u >> ./turmas/turmasOK-2011.2.csv;
grep linha ./turmas/turmas-2011.2.csv  | sort | uniq -d >> ./turmas/turmasOK-2011.2.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2012.1.csv > ./turmas/turmasOK-2012.1.csv;
while read linha
do
grep linha ./turmas/turmas-2012.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2012.1.csv;
grep linha ./turmas/turmas-2012.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2012.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2012.2.csv > ./turmas/turmasOK-2012.2.csv;
while read linha
do
grep linha ./turmas/turmas-2012.2.csv  | sort | uniq -u >> ./turmas/turmasOK-2012.2.csv;
grep linha ./turmas/turmas-2012.2.csv  | sort | uniq -d >> ./turmas/turmasOK-2012.2.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2013.1.csv > ./turmas/turmasOK-2013.1.csv;
while read linha
do
grep linha ./turmas/turmas-2013.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2013.1.csv;
grep linha ./turmas/turmas-2013.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2013.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2013.2.csv > ./turmas/turmasOK-2013.2.csv;
while read linha
do
grep linha ./turmas/turmas-2013.2.csv  | sort | uniq -u >> ./turmas/turmasOK-2013.2.csv;
grep linha ./turmas/turmas-2013.2.csv  | sort | uniq -d >> ./turmas/turmasOK-2013.2.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2014.1.csv > ./turmas/turmasOK-2014.1.csv;
while read linha
do
grep linha ./turmas/turmas-2014.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2014.1.csv;
grep linha ./turmas/turmas-2014.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2014.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2014.2.csv > ./turmas/turmasOK-2014.2.csv;
while read linha
do
grep linha ./turmas/turmas-2014.2.csv  | sort | uniq -u >> ./turmas/turmasOK-2014.2.csv;
grep linha ./turmas/turmas-2014.2.csv  | sort | uniq -d >> ./turmas/turmasOK-2014.2.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2015.1.csv > ./turmas/turmasOK-2015.1.csv;
while read linha
do
grep linha ./turmas/turmas-2015.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2015.1.csv;
grep linha ./turmas/turmas-2015.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2015.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2015.2.csv > ./turmas/turmasOK-2015.2.csv;
while read linha
do
grep linha ./turmas/turmas-2015.2.csv  | sort | uniq -u >> ./turmas/turmasOK-2015.2.csv;
grep linha ./turmas/turmas-2015.2.csv  | sort | uniq -d >> ./turmas/turmasOK-2015.2.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2016.1.csv > ./turmas/turmasOK-2016.1.csv;
while read linha
do
grep linha ./turmas/turmas-2016.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2016.1.csv;
grep linha ./turmas/turmas-2016.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2016.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2016.2.csv > ./turmas/turmasOK-2016.2.csv;
while read linha
do
grep linha ./turmas/turmas-2016.2.csv  | sort | uniq -u >> ./turmas/turmasOK-2016.2.csv;
grep linha ./turmas/turmas-2016.2.csv  | sort | uniq -d >> ./turmas/turmasOK-2016.2.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

head -n 1 ./turmas/turmas-2017.1.csv > ./turmas/turmasOK-2017.1.csv;
while read linha
do
grep linha ./turmas/turmas-2017.1.csv  | sort | uniq -u >> ./turmas/turmasOK-2017.1.csv;
grep linha ./turmas/turmas-2017.1.csv  | sort | uniq -d >> ./turmas/turmasOK-2017.1.csv;
done < ./curriculo-componente-graduacaoOK/idComponenteTI.csv;

mkdir ./turmas/OK;
mv ./turmas/turmasOK* ./turmas/OK;
#--------------------------------------------------------------------------------------------------------------------------------------------



