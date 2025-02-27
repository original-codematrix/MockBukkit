package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.AsyncCatcher;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class HumanEntityMock extends LivingEntityMock implements HumanEntity
{

	private @Nullable PlayerInventoryMock inventory = null;
	private InventoryView inventoryView;
	private @Nullable ItemStack cursor = null;
	private @NotNull GameMode gameMode = GameMode.SURVIVAL;
	private @Nullable Location lastDeathLocation = new Location(new WorldMock(), 0, 0, 0);
	protected int expLevel = 0;
	private float saturation = 5.0F;
	private int foodLevel = 20;

	protected HumanEntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	/**
	 * Assert that the player is in a specific gamemode.
	 *
	 * @param expectedGamemode The gamemode the player should be in.
	 */
	public void assertGameMode(GameMode expectedGamemode)
	{
		assertEquals(expectedGamemode, gameMode);
	}

	@Override
	public @NotNull PlayerInventory getInventory()
	{
		if (inventory == null)
		{
			inventory = (PlayerInventoryMock) Bukkit.createInventory(this, InventoryType.PLAYER);
		}
		return inventory;
	}


	@Override
	public void closeInventory()
	{
		if (inventoryView instanceof PlayerInventoryViewMock)
		{
			new InventoryCloseEvent(inventoryView).callEvent();

			if (inventoryView.getTopInventory() instanceof InventoryMock inventoryMock)
			{
				inventoryMock.removeViewer(this);
			}
		}

		// reset the cursor as it is a new InventoryView
		this.cursor = null;
		inventoryView = new SimpleInventoryViewMock(this, null, inventory, InventoryType.CRAFTING);
	}

	@Override
	public @NotNull InventoryView getOpenInventory()
	{
		return this.inventoryView;
	}

	@Override
	public void openInventory(@NotNull InventoryView inventory)
	{
		Preconditions.checkNotNull(inventory, "Inventory cannot be null");
		closeInventory();
		inventoryView = inventory;
	}

	@Override
	public InventoryView openInventory(@NotNull Inventory inventory)
	{
		AsyncCatcher.catchOp("open inventory");
		Preconditions.checkNotNull(inventory, "Inventory cannot be null");
		closeInventory();
		if (inventory instanceof InventoryMock inventoryMock)
		{
			inventoryMock.addViewers(this);
		}
		inventoryView = new PlayerInventoryViewMock(this, inventory);
		return inventoryView;
	}

	@Override
	public @NotNull ItemStack getItemOnCursor()
	{
		return cursor == null ? new ItemStack(Material.AIR, 0) : cursor.clone();
	}

	@Override
	public void setItemOnCursor(@Nullable ItemStack item)
	{
		this.cursor = item == null ? null : item.clone();
	}

	@Override
	public @Nullable Location getLastDeathLocation()
	{
		return lastDeathLocation;
	}

	@Override
	public void setLastDeathLocation(@Nullable Location location)
	{
		this.lastDeathLocation = location;
	}

	@Override
	public @NotNull GameMode getGameMode()
	{
		return this.gameMode;
	}

	@Override
	public void setGameMode(@NotNull GameMode mode)
	{
		Preconditions.checkNotNull(mode, "GameMode cannot be null");
		if (this.gameMode == mode)
			return;

		this.gameMode = mode;
	}

	@Override
	public boolean setWindowProperty(@NotNull InventoryView.Property prop, int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openWorkbench(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openEnchanting(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openMerchant(@NotNull Villager trader, boolean force)
	{
		Preconditions.checkNotNull(trader, "Trader cannot be null");
		return openMerchant((Merchant) trader, force);
	}

	@Override
	public InventoryView openMerchant(@NotNull Merchant merchant, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openAnvil(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openCartographyTable(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openGrindstone(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openLoom(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openSmithingTable(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InventoryView openStonecutter(@Nullable Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getItemInHand()
	{
		return getInventory().getItemInMainHand();
	}

	@Override
	public void setItemInHand(@Nullable ItemStack item)
	{
		getInventory().setItemInMainHand(item);
	}

	@Override
	public boolean hasCooldown(@NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getCooldown(@NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCooldown(@NotNull Material material, int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isDeeplySleeping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSleeping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSleepTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getPotentialBedLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean sleep(@NotNull Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void wakeup(boolean setSpawnLocation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Location getBedLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBlocking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public ItemStack getItemInUse()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getExpToLevel()
	{
		// Formula from https://minecraft.gamepedia.com/Experience#Leveling_up
		if (this.expLevel >= 31)
			return (9 * this.expLevel) - 158;
		if (this.expLevel >= 16)
			return (5 * this.expLevel) - 38;
		return (2 * this.expLevel) + 7;
	}

	@Override
	public @Nullable Entity releaseLeftShoulderEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Entity releaseRightShoulderEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getAttackCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean discoverRecipe(@NotNull NamespacedKey recipe)
	{
		Preconditions.checkNotNull(recipe, "Recipe cannot be null");
		return discoverRecipes(Collections.singletonList(recipe)) != 0;
	}

	@Override
	public int discoverRecipes(@NotNull Collection<NamespacedKey> recipes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean undiscoverRecipe(@NotNull NamespacedKey recipe)
	{
		Preconditions.checkNotNull(recipe, "Recipe cannot be null");
		return undiscoverRecipes(Collections.singletonList(recipe)) != 0;
	}

	@Override
	public int undiscoverRecipes(@NotNull Collection<NamespacedKey> recipes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasDiscoveredRecipe(@NotNull NamespacedKey recipe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<NamespacedKey> getDiscoveredRecipes()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getShoulderEntityLeft()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShoulderEntityLeft(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getShoulderEntityRight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShoulderEntityRight(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void openSign(@NotNull Sign sign)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean dropItem(boolean dropAll)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getExhaustion()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setExhaustion(float value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getSaturation()
	{
		return this.saturation;
	}

	@Override
	public void setSaturation(float value)
	{
		// Saturation is constrained by the current food level
		this.saturation = Math.min(getFoodLevel(), value);
	}

	@Override
	public int getFoodLevel()
	{
		return this.foodLevel;
	}

	@Override
	public void setFoodLevel(int foodLevel)
	{
		this.foodLevel = foodLevel;
	}

	@Override
	public int getSaturatedRegenRate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSaturatedRegenRate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getUnsaturatedRegenRate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setUnsaturatedRegenRate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStarvationRate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStarvationRate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}

