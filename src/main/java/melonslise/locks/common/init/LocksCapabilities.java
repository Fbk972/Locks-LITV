package melonslise.locks.common.init;

import melonslise.locks.common.capability.CapabilityProvider;
import melonslise.locks.common.capability.CapabilityStorage;
import melonslise.locks.common.capability.EmptyCapabilityStorage;
import melonslise.locks.common.capability.ILockableHandler;
import melonslise.locks.common.capability.ILockableStorage;
import melonslise.locks.common.capability.ILockableWorldGenHandler;
import melonslise.locks.common.capability.ISelection;
import melonslise.locks.common.capability.LockableHandler;
import melonslise.locks.common.capability.LockableStorage;
import melonslise.locks.common.capability.LockableWorldGenHandler;
import melonslise.locks.common.capability.Selection;
import melonslise.locks.common.capability.SerializableCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public final class LocksCapabilities
{
	@CapabilityInject(ILockableHandler.class)
	public static final Capability<ILockableHandler> LOCKABLE_HANDLER = null;

	@CapabilityInject(ILockableStorage.class)
	public static final Capability<ILockableStorage> LOCKABLE_STORAGE = null;
	
	@CapabilityInject(ILockableWorldGenHandler.class)
	public static final Capability<ILockableWorldGenHandler> LOCKABLE_WORLDGEN_HANDLER = null;

	@CapabilityInject(ISelection.class)
	public static final Capability<ISelection> SELECTION = null;

	private LocksCapabilities() {}

	public static void register()
	{
		CapabilityManager.INSTANCE.register(ILockableHandler.class, new CapabilityStorage(), () -> null);
		CapabilityManager.INSTANCE.register(ILockableStorage.class, new CapabilityStorage(), () -> null);
		CapabilityManager.INSTANCE.register(ILockableWorldGenHandler.class, new CapabilityStorage(), () -> null);
		CapabilityManager.INSTANCE.register(ISelection.class, new EmptyCapabilityStorage(), Selection::new);
	}

	public static void attachToWorld(AttachCapabilitiesEvent<World> event)
	{
		event.addCapability(LockableHandler.ID, new SerializableCapabilityProvider(LOCKABLE_HANDLER, new LockableHandler(event.getObject())));
	}
	
	public static void attachToChunk(AttachCapabilitiesEvent<Chunk> event)
	{
		event.addCapability(LockableStorage.ID, new SerializableCapabilityProvider(LOCKABLE_STORAGE, new LockableStorage(event.getObject())));
		event.addCapability(LockableWorldGenHandler.ID, new SerializableCapabilityProvider(LOCKABLE_WORLDGEN_HANDLER, new LockableWorldGenHandler(event.getObject())));
	}

	public static void attachToEntity(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
			event.addCapability(Selection.ID, new CapabilityProvider(SELECTION, new Selection()));
	}
}