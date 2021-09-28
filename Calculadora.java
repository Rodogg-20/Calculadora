/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocalculadora;

/**
 *
 * @author rdo12
 */
public class Calculadora {
    
    //este método revisa si el carácter es operador o no
    public boolean isOperator(char a){
        boolean resp=true;
        
        switch(a){
            case'+':
            case'-':
            case'/':
            case '*':
            case'(':
            case ')':
                break;
            default:
                resp=false;
                break;
        }
        return resp;
    }
    
    public boolean isOperando(char a){
        boolean resp=true;
        
        switch(a){
            case'1':
            case'2':
            case'3':
            case'4':
            case'5':
            case'6':
            case'7':
            case'8':
            case'9':
            case'0':
            case'.':
                break;
            default:
                resp=false;
                break;
        }
        return resp;
    }
    
    //verifica parentesis
    public boolean verificaParentesis(String algo) {

        boolean resp = true;
        PilaADT<Character> pila = new Pila();

        int i = 0;

        while (i < algo.length() && resp) {
            if (algo.charAt(i) == '(') {
                pila.push(algo.charAt(i));
            } else {
                if (algo.charAt(i) == ')') {
                    if (!pila.isEmpty()) {
                        pila.pop();
                    } else {
                        resp = false;
                    }
                }
                i++;
            }
            if (resp && pila.isEmpty()) { //si estan balanceados continua
                //despues verifica que no haya operadores juntos
                i = 0;
                while (i < algo.length() && resp) {
                    if (algo.charAt(i) == '+' || algo.charAt(i) == '-' || algo.charAt(i) == '*' || algo.charAt(i) == '/' || algo.charAt(i) == '.') {
                        if (!pila.isEmpty()) {
                            resp = false;
                        } else {
                            pila.push(algo.charAt(i));
                        }
                    } else {
                        if (algo.charAt(i) != '(' && algo.charAt(i) != ')') { //ignora los parentesis
                            if (algo.charAt(i) == '0' && algo.charAt(i - 1) == '/') {
                                resp = false;
                            } else {
                                try {
                                    pila.pop();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
        if (resp && pila.isEmpty()) {
            resp = true;
        }
        return resp;
    }
    
    
    public boolean jerarquiaOperandos(char operando, char tope) {
        boolean resp = true;
        switch (operando) {
            case '+':
                if (tope == '+') {
                    resp = false;
                }
                break;
            case '*':
                if (tope != '^') {
                    resp = false;
                }
                break;
            case '/':
                if (tope != '^') {
                    resp = false;
                }
                break;
            case '^':
                resp = false;
                break;
            default:
                resp = false;
                break;
        }
        return resp;
    }
    
    // Infija a posfija 
      public static String InfijaPostfija(String infija){
        String res=" ";
        
        String[] arre=infija.split("\\s");
        int i=0;
        ArrayStack<String> signos=new ArrayStack();
        ArrayStack<String> posfija=new ArrayStack();
        while(i<arre.length){
            switch(arre[i]){
                case"(": 
                    signos.push(arre[i]);
                    break;
                case")": 
                    while(!signos.isEmpty() && !signos.peek().equals("(")){
                       
                        posfija.push(signos.pop());  
                    }
                    signos.pop();
                    break;
                case"x":
                    jerarquiaOperaciones(posfija,signos,"x");
                    signos.push(arre[i]);
                    break;
                case"÷":
                    jerarquiaOperaciones(posfija,signos,"÷");
                    signos.push(arre[i]);
                    break;
                case"+":
                    jerarquiaOperaciones(posfija,signos,"+");
                    signos.push(arre[i]);
                    break;
                case"-":
                    jerarquiaOperaciones(posfija,signos,"-");
                    signos.push(arre[i]);
                    break;   
                default:
                    posfija.push(arre[i]);
                    break;
            }
            i++;
        }
        
        while(!signos.isEmpty())
            posfija.push(signos.pop());
        while(!posfija.isEmpty())
            signos.push(posfija.pop());
        while(!signos.isEmpty())
            res= res+signos.pop()+" ";

        return res;    
    }
    
    
    // verifica validez 
        public static boolean revisaSignos(String infija){
        boolean res=true;
        boolean prim=false, sec=false;
        String[] arre=infija.split("\\s");
        int i=1;
        int x=0;
        int c=arre.length-1;
        
     
        if((arre[x].equals("+") || arre[x].equals("-") || arre[x].equals("x") || arre[x].equals("÷")) ||
            (arre[c].equals("+") || arre[c].equals("-") || arre[c].equals("x") || arre[c].equals("÷")))
            res=false;
        else{
           
            while(i<arre.length && !prim && !sec){
                
                if(arre[x].equals("+") || arre[x].equals("-") || arre[x].equals("x") || arre[x].equals("÷"))
                    prim=true;
                if(arre[i].equals("+") || arre[i].equals("-") || arre[i].equals("x") || arre[i].equals("÷"))
                    sec=true;
                
                if(prim==false || sec==false){
                    prim=false;
                    sec=false;
                }
                i++;
                x++;
            }        
            if(i<arre.length)
                res=false;
        }
        return res;
    }
    
    
    //Evaluar posfija
       public static double evaluaPosfija(String posfija){
       double res;
       double aux1,aux2;
       String[] arre=posfija.split("\\s");
       ArrayStack<String> ecuacion=new ArrayStack();
       ArrayStack<String> ecuacion1=new ArrayStack();
      
        for (String arre1 : arre) {
            ecuacion.push(arre1);
        }
      
       while(!ecuacion.isEmpty()){
           ecuacion1.push(ecuacion.pop());
       }
       
       while(!ecuacion1.isEmpty()){
           

           if(!ecuacion1.peek().equals("+") && !ecuacion1.peek().equals("-") && !ecuacion1.peek().equals("x") &&
                   !ecuacion1.peek().equals("÷")){
              ecuacion.push(ecuacion1.pop());
           }
          
           else{
               aux1=Double.parseDouble(ecuacion.pop());
               
               aux2=Double.parseDouble(ecuacion.pop());
               
                switch(ecuacion1.peek()){
                    
                    case "+":
                        res=aux1+aux2;
                        ecuacion.push(res+"");
                        break;
                    case "-":
                        res=aux2-aux1;
                        ecuacion.push(res+"");
                        break;
                     case "x":
                        res=aux1*aux2;
                        ecuacion.push(res+"");
                        break;
                     case "÷":
                        res=aux2/aux1;
                        ecuacion.push(res+"");
                        break;
                }
                ecuacion1.pop();
           }
           
       }
       res=Double.parseDouble(ecuacion.pop());
       return res;
   }
  
        public void enableOperators(boolean resp){
        btnSum.setEnabled(resp);
        btnMult.setEnabled(resp);
        btnDiv.setEnabled(resp);
        btnRest.setEnabled(resp);
        btnPunto.setEnabled(resp);
    }
    
    //GUI

       private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        
    }                                    

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"1");   
    }                                    

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"2");
    }                                    

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {                                     
       enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"3");
    }                                    

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"4");
    }                                    

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"5");
    }                                    

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"6");
    }                                    

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"7");
    }                                    

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"8");
    }                                    

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"9");
    }                                    

    private void btnRestActionPerformed(java.awt.event.ActionEvent evt) {                                        
        
        enableOperators(false);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+" - ");
    }                                       

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {                                     
       enableOperators(true);
        ecuacion=txt1.getText();
       txt1.setText(ecuacion+"0"); 
    }                                    

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {                                         
        txt1.setText("");
    }                                        

    private void btnSumActionPerformed(java.awt.event.ActionEvent evt) {                                       
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" + ");
    }                                      

    private void btnMultActionPerformed(java.awt.event.ActionEvent evt) {                                        
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" x ");
    }                                       

    private void btnDivActionPerformed(java.awt.event.ActionEvent evt) {                                       
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" ÷ ");
    }                                      

    private void btnParIActionPerformed(java.awt.event.ActionEvent evt) {                                        
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+"( ");
    }                                       

    private void btnParDActionPerformed(java.awt.event.ActionEvent evt) {                                        
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+" )");
    }                                       

    private void btnPuntoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        enableOperators(false);
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+".");
    }                                        

    private void btnSigNegActionPerformed(java.awt.event.ActionEvent evt) {                                          
        ecuacion=txt1.getText();
        txt1.setText(ecuacion+"-");
    }                                         

    private void btnIgualActionPerformed(java.awt.event.ActionEvent evt) {                                         
    if(analisisParentesis(txt1.getText()))
            if(revisaSignos(txt1.getText())){
                try{
                    ecuacion =InfijaPostfija(txt1.getText());
                    num=resuelveEcuacion(ecuacion);
                    txt1.setText(num+"");
                }
                catch(Exception e){
                txt1.setText("Error en sintaxis o la ecuacion esta vacia");
            }
            }
            else
                txt1.setText("Error en balanceo de signos");
        else
            txt1.setText("Error en balanceo de parentesis");       
    }                                        

    public static void main(String args[]) {
     
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       

        
        java.awt.EventQueue.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnDiv;
    private javax.swing.JButton btnIgual;
    private javax.swing.JButton btnMult;
    private javax.swing.JButton btnParD;
    private javax.swing.JButton btnParI;
    private javax.swing.JButton btnPunto;
    private javax.swing.JButton btnRest;
    private javax.swing.JButton btnSigNeg;
    private javax.swing.JButton btnSum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txt1;    
        
    
    
}
