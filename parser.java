import java.util.Stack;

public class parser
{
	private static String expression,token="";
	private static int expressionIndex=0,command=-1;
	private static boolean valid=true,complete=false;
	private static Stack<String> stack = new Stack<String>();
	
	public static void main(String[] args)
	{
		expression = args[0].concat("$");
		push("0");
		setToken();
		while(!complete && valid)
		{
			switchCommand();
		}
		if(valid){
			Calculator x = new Calculator();
			 String temp = Double.toString(x.evaluate(args[0]));
			System.out.print("Valid expressionression = " +  temp );
			}
		else
			System.out.println("expressionression is not Valid");
	}
	
	
	private static void print(){
		System.out.println(stack.toString() + " " + token+ expression.substring(expressionIndex));
	
	}
	private static String pop()
	{
		String result =	stack.pop();
		//print();
		return result;
	}
	private static void push(String s)
	{
		stack.push(s);
		//print();
	}
	private static boolean isOperator(char c)
	{
		if(c=='+' || c=='-' || c=='*' || c=='/' || c=='(' || c==')' || c=='$')
			return true;
		else
			return false;
	}
	private static boolean isInt(char c)
	{
		if(c=='1' || c=='2' || c=='3' || c=='4' || c=='5'|| c=='6' || c=='7' || c=='8' || c=='9' || c=='0')
			return true;
		else
			return false;
	}
	private static boolean isInt()
	{
		char c = token.charAt(0);
		if(c=='1' || c=='2' || c=='3' || c=='4' || c=='5'|| c=='6' || c=='7' || c=='8' || c=='9' || c=='0')
			return true;
		else
			return false;
	}
	private static void setToken()
	{
		if(isOperator(expression.charAt(expressionIndex)))
		{
			token = "" + expression.charAt(expressionIndex);
			expressionIndex++;
		}
		else if(isInt(expression.charAt(expressionIndex)))
		{
			token = "";
			while(isInt(expression.charAt(expressionIndex)))
			{
				token += expression.charAt(expressionIndex);
				expressionIndex++;
			}
		}
		else
		{
			valid=false;
			expressionIndex++;
			setToken();
		}
	}
	private static boolean tokenIs(char c)
	{
		if(token.charAt(0)==c)
			return true;
		return false;
	}
	private static void switchCommand()
	{
		if(isInt(stack.peek().charAt(0))){ print();
			command=Integer.parseInt(stack.peek());}
		else
			command=-1;
		switch(command)
		{
		case 0:
			f0();
			break;
		case 1:
			f1();
			break;
		case 2:
			f2();
			break;
		case 3:
			f3();
			break;
		case 4:
			f4();
			break;
		case 5:
			f5();
			break;
		case 6:
			f6();
			break;
		case 7:
			f7();
			break;
		case 8:
			f8();
			break;
		case 9:
			f9();
			break;
		case 10:
			f10();
			break;
		case 11:
			f11();
			break;
		default:
			System.out.println("Invalid action");
			valid=false;
			break;
		}
	}
	private static void pushE()
	{
		if(stack.peek().equals("0"))
		{
			push("E");
			push("1");
		}
		else if(stack.peek().equals("4"))
		{
			push("E");
			push("8");
		}
		else
			valid=false;
	}
	private static void pushT()
	{
		if(stack.peek().equals("0"))
		{
			push("T");
			push("2");
		}
		else if(stack.peek().equals("4"))
		{
			push("T");
			push("2");
		}
		else if(stack.peek().equals("6"))
		{
			push("T");
			push("9");
		}
		else
			valid=false;
	}
	private static void pushF()
	{
		if(stack.peek().equals("0"))
		{
			push("F");
			push("3");
		}
		else if(stack.peek().equals("4"))
		{
			push("F");
			push("3");
		}
		else if(stack.peek().equals("6"))
		{
			push("F");
			push("3");
		}
		else if(stack.peek().equals("7"))
		{
			push("F");
			push("10");
		}
		else
			valid=false;
	}
	private static void f0()
	{
		if(isInt())
		{
			//shift 5
			push(token);
			push("5");
			setToken();
		}
		else if(tokenIs('('))
		{
			//shift 4
			push(token);
			push("4");
			setToken();
		}
	}
	private static void f1()
	{
		if(tokenIs('+') || tokenIs('-'))
		{
			//shift 6
			push(token);
			push("6");
			setToken();
		}
		else if(tokenIs('$'))
		{
			//accept
			complete=true;
		}
		else
		{
			valid=false;
		}
	}
	
	private static void f2()
	{
		if(tokenIs('+') || tokenIs('-') || tokenIs(')') || tokenIs('$'))
		{
			//E->T
			pop();
			if(pop().charAt(0)!='T')
			{
				valid=false;
			}
			pushE();
		}
		else if(tokenIs('*') || tokenIs('/'))
		{
			//shift 7
			push(token);
			push("7");
			setToken();
		}
		else
		{
			valid=false;
		}
	}
	private static void f3()
	{
		if(tokenIs('+') || tokenIs('-') || tokenIs('*') || tokenIs('/') || tokenIs(')') || tokenIs('$'))
		{
			//T->F
			pop();
			if(pop().charAt(0)!='F')
			{
				valid=false;
			}
			pushT();
		}
		else
		{
			valid=false;
		}
	}
	private static void f4()
	{
		if(isInt())
		{
			//shift 5
			push(token);
			push("5");
			setToken();
		}
		else if(tokenIs('('))
		{
			//shift 4
			push(token);
			push("4");
			setToken();
		}
		else
		{
			valid=false;
		}
	}
	private static void f5()
	{
		if(tokenIs('+') || tokenIs('-') || tokenIs('*') || tokenIs('/') || tokenIs(')') || tokenIs('$'))
		{
			//F->n
			pop();
			if(!isInt(pop().charAt(0)))
			{
				valid=false;
			}
			pushF();
		}
		else
		{
			valid=false;
		}
	}
	private static void f6()
	{
		if(isInt())
		{
			//shift 5
			push(token);
			push("5");
			setToken();
		}
		else if(tokenIs('('))
		{
			//shift 4
			push(token);
			push("4");
			setToken();
		}
		else
		{
			valid=false;
		}
	}
	private static void f7()
	{
		if(isInt())
		{
			//shift 5
			push(token);
			push("5");
			setToken();
		}
		else if(tokenIs('('))
		{
			//shift 4
			push(token);
			push("4");
			setToken();
		}
		else
		{
			valid=false;
		}
	}
	private static void f8()
	{
		if(tokenIs('+') || tokenIs('-'))
		{
			//shift 6
			push(token);
			push("6");
			setToken();
		}
		else if(tokenIs(')'))
		{
			//shift 11
			push(token);
			push("11");
			setToken();
		}
		else
		{
			valid=false;
		}
	}
	private static void f9()
	{
		if(tokenIs('+') || tokenIs('-') || tokenIs(')') || tokenIs('$'))
		{
			//E->E+T
			pop();
			if(pop().charAt(0)!='T')
			{
				valid=false;
			}
			pop();
			char x = pop().charAt(0);
			if(x!='+' && x!='-')
			{
				valid=false;
			}
			pop();
			if(pop().charAt(0)!='E')
			{
				valid=false;
			}
			pushE();
		}
		else if(tokenIs('*'))
		{
			//shift 7
			push(token);
			push("7");
			setToken();
		}
		else
			valid=false;
	}
	private static void f10()
	{
		if(tokenIs('+') || tokenIs('-') || tokenIs('*') || tokenIs('/') || tokenIs(')') || tokenIs('$'))
		{
			//T->T*F
			pop();
			if(pop().charAt(0)!='F')
			{
				valid=false;
			}
			pop();
			char x = pop().charAt(0);
			if(x!='*' && x!='/')
			{
				valid=false;
			}
			pop();
			if(pop().charAt(0)!='T')
			{
				valid=false;
			}
			pushT();
		}
		else
			valid=false;
	}
	private static void f11()
	{
		if(tokenIs('+') || tokenIs('-') || tokenIs('*') || tokenIs('-') || tokenIs(')') || tokenIs('$'))
		{
			//F->(E)
			pop();
			if(pop().charAt(0)!=')')
			{
				valid=false;
			}
			pop();
			if(pop().charAt(0)!='E')
			{
				valid=false;
			}
			pop();
			if(pop().charAt(0)!='(')
			{
				valid=false;
			}
			pushF();
		}
		else
			valid=false;
	}
}