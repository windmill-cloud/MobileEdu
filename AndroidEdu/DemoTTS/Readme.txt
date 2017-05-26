This template code provides an automated architecture to seamlessly change orientation while maintaining state. In a master-detail
view, the master view lists a set of actions that can be taken while the detail view shows each action. In the case of a portrait
orientation, only the master view is visible and selecting an action moves to a new Activity to show the detail view. In the case
of a horizontal orientation, both the master view and the detail view are visible, and selecting an action updates the detail view
to show the selected action.

In this template, the list of actions are defined in strings.xml under the string array named actions. These are displayed as options
in the master view. When a list item is clicked, it will spawn the corresponding fragment. In order for this to work, you must insert
a switch case in FragmentFactory's createFragment function that returns a subclass of SavableFragment. The case matches the name
of the action specified in strings.xml. If an action is clicked that does not return anything from the createFragment function, it
crashes.

SavableFragment is a direct child of Fragment but with two new functions: saveState(Bundle bundle) and restoreState(Bundle bundle).
When extending SavableFragment, you will still need to override onCreateView as with any other Fragment. The saveState function is
called automatically when the architecture detects an orientation change. You should put any state that you want to save into the
provided Bundle. The restoreState function is called automatically when trying to recreate your Fragment after an orientation change.
You should restore all the state you stored in saveState. Be aware that restoreState will be called before onCreateView, so any
variables that are assigned there will be null, and therefore cannot be used. Most commonly, this will be variables that store Views
that you define in your layouts.

After creating a child of SavableFragment, you would then add a case in FragmentFactory that returns a new instance of your child.
When the action that corresponds to it is clicked, it will be displayed automatically and orientation change will be handled.