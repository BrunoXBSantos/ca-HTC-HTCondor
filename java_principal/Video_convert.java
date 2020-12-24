import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
//package net.bramp.ffmpeg.probe;



public class Video_convert {

    public static void dividir_submit(int n, String name) throws IOException {
        String filename = String.format("divide%d.submit", n);
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("Universe = vanilla%n");
        gravarArq.printf("executable = divide%d.sh%n",n);

        //Runtime.getRuntime().exec("mkdir dividir1");
        
        gravarArq.printf("log = out/log/log-divide%d-$(Process).log%n",n);
        gravarArq.printf("output = out/output/out-divide%d-$(Process).txt%n",n);
        gravarArq.printf("error = out/error/erro-divide%d-$(Process).txt%n",n);
        gravarArq.printf("request_cpus = 1%n");
        gravarArq.printf("request_memory = 1024%n");
        gravarArq.printf("%n");
        gravarArq.printf("should_transfer_files  = Yes%n"); 
        gravarArq.printf("when_to_transfer_output = ON_EXIT%n");
        gravarArq.printf("transfer_input_files  = videos/%s%n",name);
        //gravarArq.printf("%n");
        gravarArq.printf("queue%n");
        arq.close();
    }

    public static void dividir_sh(int n, String name, int hora, int minuto, int segundo, int tempo_corte, String extensao_saida) throws IOException {
        String filename = String.format("divide%d.sh", n);
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("#!/bin/bash%n");
        gravarArq.printf("tempo_corte=%d%n",tempo_corte);
        gravarArq.printf("nome_video_entrada=%s%n",name);
        gravarArq.printf("hora=%d%n",hora);
        gravarArq.printf("minuto=%d%n",minuto);
        gravarArq.printf("segundo=%d%n",segundo);
        gravarArq.printf("ffmpeg -i %s -ss %d:%d:%d -t %d %d.%s%n",name,hora, minuto, segundo, tempo_corte,n , extensao_saida); 
        arq.close();
    }

    public static void quality_submit(int n, String extensao) throws IOException {
        String filename = String.format("quality%d.submit", n);
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("Universe = vanilla%n");
        gravarArq.printf("executable = quality%d.sh%n",n);

        //Runtime.getRuntime().exec("mkdir dividir1");
        
        gravarArq.printf("log = out/log/log-quality%d-$(Process).log%n",n);
        gravarArq.printf("output = out/output/out-quality%d-$(Process).txt%n",n);
        gravarArq.printf("error = out/error/erro-quality%d-$(Process).txt%n",n);
        gravarArq.printf("request_cpus = 1%n");
        gravarArq.printf("request_memory = 1024%n");
        gravarArq.printf("%n");
        gravarArq.printf("should_transfer_files  = Yes%n"); 
        gravarArq.printf("when_to_transfer_output = ON_EXIT%n");
        gravarArq.printf("transfer_input_files  = %d.%s%n",n, extensao);
        //gravarArq.printf("%n");
        gravarArq.printf("queue%n");
        arq.close();
    }

    public static void quality_sh(int n, String extensao_saida, int quali_final) throws IOException {
        String filename = String.format("quality%d.sh", n);
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("#!/bin/bash%n");
        gravarArq.printf("ffmpeg -i ./%d.%s -vf scale=-1:%d %d_%d.%s%n",n, extensao_saida, quali_final, n, quali_final, extensao_saida); 
        arq.close();
    }

    public static void join_submit(int n, int quali_final, String extensao) throws IOException {
        String filename = String.format("join.submit");
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("Universe = vanilla%n");
        gravarArq.printf("executable = join.sh%n");

        //Runtime.getRuntime().exec("mkdir dividir1");
        
        gravarArq.printf("log = out/log/log-join-$(Process).log%n");
        gravarArq.printf("output = out/output/out-join-$(Process).txt%n");
        gravarArq.printf("error = out/error/erro-join-$(Process).txt%n");
        gravarArq.printf("request_cpus = 1%n");
        gravarArq.printf("request_memory = 1024%n");
        gravarArq.printf("%n");
        gravarArq.printf("should_transfer_files  = Yes%n"); 
        gravarArq.printf("when_to_transfer_output = ON_EXIT%n");
        String transfer = new String("transfer_input_files  = list.txt");
        for(int i=1; i<=n; i++){
            String s = String.format(", %d_%d.%s",i, quali_final, extensao);
            transfer = transfer.concat(s);
        }
        //gravarArq.printf("%n");
        gravarArq.printf(transfer + "%n");
        gravarArq.printf("queue%n");
        arq.close();
    }

    public static void join_sh(String name, String extensao_saida, int quali_final) throws IOException {
        String filename = String.format("join.sh");
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("#!/bin/bash%n");
        gravarArq.printf("ffmpeg -f concat -safe 0 -i list.txt -c copy %s_%d.%s%n",name, quali_final, extensao_saida); 
        arq.close();
    }

    public static void list(int n, int quali_final, String extensao_saida) throws IOException {
        String filename = String.format("list.txt");
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("#lista dos filmes%n");
        for(int i=1; i<=n; i++){
            gravarArq.printf("file '%d_%d.%s'%n",i,quali_final, extensao_saida);
        }
        arq.close();
    }

    public static void dag(int n) throws IOException {
        String filename = String.format("job.dag");
        FileWriter arq = new FileWriter(filename);
        PrintWriter gravarArq = new PrintWriter(arq);
        for(int i=1; i<=n; i++){
            gravarArq.printf("JOB Divide%d divide%d.submit%n",i, i);
        }
        for(int i=1; i<=n; i++){
            gravarArq.printf("JOB Quality%d quality%d.submit%n",i, i);
        }
        gravarArq.printf("JOB Join join.submit%n");
        gravarArq.printf("%n");
        for(int i=1; i<=n; i++){
            gravarArq.printf("PARENT Divide%d CHILD Quality%d%n",i, i);
        }
        String temp = new String("PARENT ");
        for(int i=1; i<=n; i++){
            String s = String.format("Quality%d ",i);
            temp = temp.concat(s);
        }
        gravarArq.printf(temp+" CHILD Join%n");
        arq.close();
    }

    public static void main(String[] args) throws IOException {
        //Runtime run = Runtime.getRuntime();
        
        Scanner keyboard = new Scanner(System.in);

        System.out.print("\n    Introduza o nome do filme:  "); 
        String name = keyboard.nextLine();

        System.out.print("\n    Introduza extensao do filme:  "); 
        String extensao = keyboard.nextLine();

        System.out.print("\n    Introduza qualidade original  "); 
        Integer qual_original = keyboard.nextInt();

        System.out.print("\n    Introduza qualidade final  "); 
        Integer qual_final = keyboard.nextInt();


        System.out.print("\n    Introduza num de maquimas:  "); 
        Integer machines = keyboard.nextInt();

        System.out.print("\n    Introduza tempo total:  "); 
        Integer tempo_total = keyboard.nextInt();

        System.out.printf("\n    Filme a converter: \n        %s.%s | %dp -> %dp | %d maquinas\n",name, extensao, qual_original, qual_final, machines);        
         
        int segundos_atuais = 0;
        int segundo;
        int hora;
        int minuto;
        int tempo_corte = tempo_total/machines;
        int i=1;

        Runtime.getRuntime().exec("mkdir out");
        Runtime.getRuntime().exec("mkdir out/output");
        Runtime.getRuntime().exec("mkdir out/log");
        Runtime.getRuntime().exec("mkdir out/error");

        for(i=1; i<=machines; i++){
            hora = segundos_atuais / 3600;
            minuto = segundos_atuais % 3600  / 60;
            segundo = segundos_atuais % 3600  % 60;
            //System.out.printf("\n%d : %d : %d",hora, minuto, segundo);
            String completo = new String(name + "." + extensao);
            dividir_submit(i, completo);
            dividir_sh(i, completo, hora, minuto, segundo, tempo_corte, extensao);
            quality_submit(i, extensao);
            quality_sh(i, extensao, qual_final);
            segundos_atuais = tempo_corte * i;
        }
        join_sh(name, extensao, qual_final);
        join_submit(machines, qual_final, extensao);
        list(machines,qual_final,extensao);
        dag(machines);

        System.out.print("\n    Setup configurado. Para submeter: condor_submit_dag job.dag\n\n");
    }
} 

/*

FFmpegProbeResult probeResult = ffprobe.probe("input.mp4");

        FFmpegFormat format = probeResult.getFormat();
System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs", 
	format.filename, 
	format.format_long_name,
	format.duration
);

 try {
            run.exec(command);
            System.out.println("x");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/ 
