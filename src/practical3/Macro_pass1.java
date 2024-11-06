package practical3;

import java.io.*; 
import java.util.*; 
public class Macro_pass1 { 
static List<String>MDT; 
static Map<String,String>MNT; 
static int mntPtr,mdtPtr; 
static Map<String,String>ALA; 
public static void main (String []args) { 
try { 
pass1(); 
}catch(Exception ex) { 
ex.printStackTrace(); 
} 
} 
static void pass1() throws Exception{ 
MDT=new ArrayList<String>(); 
MNT=new LinkedHashMap<String,String>(); 
ALA=new HashMap<String,String>(); 
mntPtr=0;mdtPtr=0; 
BufferedReader input=new BufferedReader(new InputStreamReader(new 
FileInputStream("src\\practical3\\input.txt"))); 
PrintWriter out_pass1=new PrintWriter(new 
FileWriter("src\\practical3\\OUTPUT1.txt"),true); 
  PrintWriter out_mnt=new PrintWriter(new 
FileWriter("src\\practical3\\MNT.txt"),true); 
  PrintWriter out_mdt=new PrintWriter(new 
FileWriter("src\\practical3\\MDT.txt"),true); 
  String s; 
  boolean processingMacroDefinition=false; 
  boolean processMacroName=false; 
  System.out.println("======pass 1 output===="); 
  while ((s=input.readLine())!= null){ 
   String s_arr[]=tokenizeString(s," "); 
   String curToken=s_arr[0]; 
   if(curToken.equalsIgnoreCase("MACRO")) { 
    processingMacroDefinition=true; 
    processMacroName=true; 
   } 
   else if(processingMacroDefinition==true) { 
    if(curToken.equalsIgnoreCase("MEND")) { 
     MDT.add(mdtPtr++,s); 
     processingMacroDefinition=false; 
     continue; 
   } 
    if(processMacroName==true) { 
     MNT.put(curToken, mdtPtr+""); 
     mntPtr++; 
     processMacroName=false; 
     processArgumentList(s_arr[1]); 
     MDT.add(mdtPtr,s); 
     mdtPtr++; 
     continue; 
      
    } 
    String indexedArgList=processArguments(s_arr[1]); 
    MDT.add(mdtPtr++,curToken+" "+indexedArgList); 
  } 
   else{ 
    System.out.println(s); 
    out_pass1.println(s); 
    } 
   } 
  input.close(); 
  System.out.println("====MNT===="); 
  Iterator<String>itMNT=MNT.keySet().iterator(); 
  String key,mntRow,mdtRow; 
  while(itMNT.hasNext()) { 
   key=(String)itMNT.next(); 
   mntRow=key+" "+MNT.get(key); 
   System.out.println(mntRow); 
   out_mnt.println(mntRow); 
   } 
  System.out.println("=====MDT====="); 
  for(int i=0;i<MDT.size();i++) { 
   mdtRow=i+" "+MDT.get(i); 
   System.out.println(mdtRow); 
   out_mdt.println(mdtRow); 
   } 
  out_pass1.close(); 
  out_mnt.close(); 
  out_mdt.close(); 
   
  } 
 static void processArgumentList(String argList) { 
  StringTokenizer st= new StringTokenizer(argList, ",",false); 
  ALA.clear(); 
  int argCount =st.countTokens(); 
  String curArg; 
  for(int i=1;i<=argCount;i++) { 
   curArg=st.nextToken(); 
   if(curArg.contains("=")) { 
    curArg=curArg.substring(0,curArg.indexOf("=")); 
   } 
   ALA.put(curArg, "#"+i); 
  } 
 } 
 static String processArguments(String argList) { 
  StringTokenizer st=new StringTokenizer(argList,",",false); 
  int argCount=st.countTokens(); 
String curArg,argIndexed; 
for(int i=0;i<argCount;i++) { 
curArg=st.nextToken(); 
argIndexed=ALA.get(curArg); 
argList=argList.replaceAll(curArg,argIndexed); 
} 
return argList; 
} 
static String[]tokenizeString(String str,String seperator){ 
StringTokenizer st=new StringTokenizer(str,seperator,false); 
String s_arr[]=new String [st.countTokens()]; 
for(int i=0;i<s_arr.length;i++) { 
s_arr[i]=st.nextToken(); 
} 
return s_arr; 
} 
} 



 
 
 
 
 
 
 
 
 