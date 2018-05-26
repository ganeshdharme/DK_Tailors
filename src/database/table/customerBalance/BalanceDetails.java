package database.table.customerBalance;

public class BalanceDetails
{

	private int myCustomerId;
	private int myPendingAmount;
	private int myTotalAmount;

	public BalanceDetails(int customerId, int pendingAmount, int totalAmount)
	{
		myCustomerId = customerId;
		myPendingAmount = pendingAmount;
		myTotalAmount = totalAmount;
	}

	public int GetCustomerId()
	{
		return myCustomerId;
	}

	public int GetPendingAmount()
	{
		return myPendingAmount;
	}

	public void SetPendingAmount(int amount)
	{
		myPendingAmount = amount;
	}

	public int GetTotalAmount()
	{
		return myTotalAmount;
	}

	public void SetTotalAmount(int amount)
	{
		myTotalAmount = amount;
	}

}
