/**
 * Класс связей между объектами
 */
public class Link
{
	Thing inbound;
	Thing outbound;

/**
 * new Link(a,b) creates a one-directional link from a to b
 * @param inbound origin
 * @param outbound destination
 */
	public Link (Thing inbound, Thing outbound)
	{
		this.inbound=inbound;
		this.outbound=outbound;
	}
}
