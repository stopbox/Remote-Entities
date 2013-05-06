package de.kumpelblase2.remoteentities.api.thinking.goals;

import net.minecraft.server.v1_5_R3.EntityTameableAnimal;
import net.minecraft.server.v1_5_R3.Vec3D;
import org.bukkit.Location;
import de.kumpelblase2.remoteentities.api.RemoteEntity;
import de.kumpelblase2.remoteentities.api.thinking.DesireBase;
import de.kumpelblase2.remoteentities.api.thinking.DesireType;
import de.kumpelblase2.remoteentities.nms.RandomPositionGenerator;

public class DesireWanderAround extends DesireBase
{
	protected double m_xPos;
	protected double m_yPos;
	protected double m_zPos;
	
	public DesireWanderAround(RemoteEntity inEntity)
	{
		super(inEntity);
		this.m_type = DesireType.PRIMAL_INSTINCT;
	}
	
	@Override
	public boolean shouldExecute()
	{
		if(this.getEntityHandle() == null)
			return false;
		
		if(this.getEntityHandle().aH() >= 100)
			return false;
		else if(this.getEntityHandle().aE().nextInt(120) != 0)
			return false;
		else if(this.getEntityHandle() instanceof EntityTameableAnimal && ((EntityTameableAnimal)this.getEntityHandle()).isSitting())
			return false;
		else
		{
			Vec3D vector = RandomPositionGenerator.a(this.getEntityHandle(), 10, 7);
			if(vector == null)
				return false;
			else
			{
				this.m_xPos = vector.c;
				this.m_yPos = vector.d;
				this.m_zPos = vector.e;
				return true;
			}
		}
	}
	
	@Override
	public boolean canContinue()
	{
		return !this.getEntityHandle().getNavigation().f();
	}
	
	@Override
	public void startExecuting()
	{
		this.getRemoteEntity().move(new Location(this.getRemoteEntity().getBukkitEntity().getWorld(), this.m_xPos, this.m_yPos, this.m_zPos));
	}
}
