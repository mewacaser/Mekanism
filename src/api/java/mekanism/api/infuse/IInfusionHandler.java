package mekanism.api.infuse;

import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import mekanism.api.Action;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IInfusionHandler {

    /**
     * Returns the number of infusion storage units ("tanks") available
     *
     * @return The number of tanks available
     */
    int getInfusionTankCount();

    /**
     * Returns the {@link InfusionStack} in a given tank.
     *
     * <p>
     * <strong>IMPORTANT:</strong> This {@link InfusionStack} <em>MUST NOT</em> be modified. This method is not for
     * altering internal contents. Any implementers who are able to detect modification via this method should throw an exception. It is ENTIRELY reasonable and likely
     * that the stack returned here will be a copy.
     * </p>
     *
     * <p>
     * <strong><em>SERIOUSLY: DO NOT MODIFY THE RETURNED INFUSION STACK</em></strong>
     * </p>
     *
     * @param tank Tank to query.
     *
     * @return {@link InfusionStack} in a given tank. {@link InfusionStack#EMPTY} if the tank is empty.
     */
    InfusionStack getInfusionInTank(int tank);

    /**
     * Overrides the stack in the given tank. This method may throw an error if it is called unexpectedly.
     *
     * @param tank  Tank to modify
     * @param stack {@link InfusionStack} to set tank to (may be empty).
     *
     * @throws RuntimeException if the handler is called in a way that the handler was not expecting.
     **/
    void setInfusionInTank(int tank, InfusionStack stack);

    /**
     * Retrieves the maximum amount of infuse type that can be stored in a given tank.
     *
     * @param tank Tank to query.
     *
     * @return The maximum infusion type amount held by the tank.
     */
    int getInfusionTankCapacity(int tank);

    /**
     * <p>
     * This function should be used instead of simulated insertions in cases where the contents and state of the tank are irrelevant, mainly for the purpose of automation
     * and logic.
     * </p>
     * <ul>
     * <li>isInfusionValid is false when insertion of the infuse type is never valid.</li>
     * <li>When isInfusionValid is true, no assumptions can be made and insertion must be simulated case-by-case.</li>
     * <li>The actual infuse type in the tank, its fullness, or any other state are <strong>not</strong> considered by isInfusionValid.</li>
     * </ul>
     *
     * @param tank  Tank to query.
     * @param stack Stack to test with for validity
     *
     * @return true if the tank can accept the {@link InfusionStack}, not considering the current state of the tank. false if the tank can never support the given {@link
     * InfusionStack} in any situation.
     */
    boolean isInfusionValid(int tank, InfusionStack stack);

    /**
     * <p>
     * Inserts a {@link InfusionStack} into a given tank and return the remainder. The {@link InfusionStack} <em>should not</em> be modified in this function!
     * </p>
     * Note: This behaviour is subtly different from {@link IFluidHandler#fill(FluidStack, FluidAction)}
     *
     * @param tank   Tank to insert to.
     * @param stack  {@link InfusionStack} to insert. This must not be modified by the tank.
     * @param action The action to perform, either {@link Action#EXECUTE} or {@link Action#SIMULATE}
     *
     * @return The remaining {@link InfusionStack} that was not inserted (if the entire stack is accepted, then return an empty {@link InfusionStack}). May be the same as
     * the input {@link InfusionStack} if unchanged, otherwise a new {@link InfusionStack}. The returned {@link InfusionStack} can be safely modified after
     */
    InfusionStack insertInfusion(int tank, InfusionStack stack, Action action);

    /**
     * Extracts a {@link InfusionStack} from a specific tank in this handler.
     * <p>
     * The returned value must be empty if nothing is extracted, otherwise its stack size must be less than or equal to {@code amount}.
     * </p>
     *
     * @param tank   Tank to extract from.
     * @param amount Amount to extract (may be greater than the current stack's max limit)
     * @param action The action to perform, either {@link Action#EXECUTE} or {@link Action#SIMULATE}
     *
     * @return {@link InfusionStack} extracted from the tank, must be empty if nothing can be extracted. The returned {@link InfusionStack} can be safely modified after,
     * so the tank should return a new or copied stack.
     */
    InfusionStack extractInfusion(int tank, int amount, Action action);
}