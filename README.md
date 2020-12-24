# ca-HTC-HTCondor

No âmbito da unidade curricular ”Computação Avançada”do perfil ”Ciência de Dados”, foi-nos proposta a realização de um trabalho prático, cujo objetivo seria instalar e configurar um cluster de HTCondor com o mı́nimo de 3 nós e utilizar o mesmo na resolução de uma tarefa de processamento.

Sendo que para isso, e de forma a comprovar o funcionamento do cluster, deveremos desenvolver uma aplicação resizing de vı́deo, capaz de converter um vı́deo com resolução fullHD (1080p) num vı́deo com resolução SD (720p).

O Cluster foi implementado no PC pessoal recorrendo a 3 máquinas virtuais virtualBox. Para criar as máquinas virtuais recorremos ao software vagrant. 
vagrant up - inicia as máuinas 
vagrant halt desliga as máquinas

Deve-se enviar a pasta java_principal para o interior do condor-1 através do comando vagrant uplpad.
