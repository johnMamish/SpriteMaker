package spritemaker;

public class BadCanvasSizeError extends Throwable
{
	public String foo;
	
	public BadCanvasSizeError(String message)
	{
		foo = message;
	}
}
