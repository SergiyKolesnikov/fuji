// constraints
Violet : Additional* Help* [HelpMenu] Window* [WindowMenu] View* [ViewMenu] Edit* [EditMenu] ClassDiagram* SequenceDiagram* StateDiagram* ObjectDiagram* UseCaseDiagram* [DiagramSupport] File* [NewFile] [FileMenu] [Read] [InternalFrame] [FileUtility] [ExtensionFilter] [GraphUtility] [MenuResources] base :: VioletDef ;

Additional : CommandLine
	| VersionChecker
	| VioletFilter
	| ExportFilter
	| SetTitle
	| Preferences ;

Help : About
	| License ;

Window : CloseWindow
	| RestoreWindow
	| MaximizeWindow
	| PreviousWindow
	| NextWindow ;

View : LookAndFeel
	| ZoomOut
	| ZoomIn
	| GrowDrawingArea
	| ClipDrawingArea
	| SmallerGrid
	| LargerGrid
	| HideGrid ;

Edit : EditProperties
	| DeleteItem
	| SelectNext
	| SelectPrevious ;

ClassDiagram : ClassNode
	| InterfaceNode
	| PackageNode
	| ClassNoteNode
	| ClassDependencyEdge
	| ClassInheritanceEdge
	| ClassAggregationEdge
	| ClassAssociationEdge
	| ClassCompositionEdge
	| ClassInterfaceEdge
	| ClassNoteEdge ;

SequenceDiagram : SequenceImplicitParameterNode
	| SequenceCallNode
	| SequenceNoteNode
	| SequenceCallEdge
	| SequenceReturnEdge
	| SequenceNoteEdge ;

StateDiagram : StateNode
	| InitialStateNode
	| FinalStateNode
	| StateNoteNode
	| StateTransitionEdge
	| StateNoteEdge ;

ObjectDiagram : ObjectNode
	| ObjectFieldNode
	| ObjectNoteNode
	| ObjectReferenceEdge
	| ObjectAssociationEdge
	| ObjectNoteEdge ;

UseCaseDiagram : UseCaseActorNode
	| UseCaseNode
	| UseCaseNoteNode
	| UseCaseAssociationEdge
	| UseCaseExtendRelationEdge
	| UseCaseIncludeRelationEdge
	| UseCaseGeneralizationEdge
	| UseCaseNoteEdge ;

File : ExitV
	| Print
	| ExportImage
	| SaveFile
	| SaveAs
	| RecentFile
	| OpenFile ;

%%

File or NewFile or Edit or View or Window or Help or Additional implies MenuResources and GraphUtility and ExtensionFilter and FileUtility and InternalFrame and Read ;
File or NewFile implies FileMenu ;
Edit implies EditMenu ;
View implies ViewMenu ;
Window implies WindowMenu ;
Help implies HelpMenu ;
ClassDiagram or SequenceDiagram or StateDiagram or ObjectDiagram or UseCaseDiagram implies DiagramSupport and NewFile ;
RecentFile implies OpenFile ;
SaveFile implies SaveAs ;
ExportFilter implies ExtensionFilter and ExportImage ;
VioletFilter implies ExtensionFilter ;
CommandLine implies OpenFile ;
ClassDependencyEdge or ClassInheritanceEdge or ClassAggregationEdge or ClassAssociationEdge or ClassCompositionEdge or ClassInterfaceEdge implies ClassNode ;
ClassNoteEdge implies ClassNoteNode ;
SequenceCallEdge or SequenceReturnEdge implies SequenceCallNode ;
SequenceNoteEdge implies SequenceNoteNode ;
StateTransitionEdge implies StateNode ;
StateNoteEdge implies StateNoteNode ;
ObjectReferenceEdge or ObjectAssociationEdge implies ObjectNode ;
ObjectNoteEdge implies ObjectNoteNode ;
UseCaseAssociationEdge or UseCaseExtendRelationEdge or UseCaseIncludeRelationEdge or UseCaseGeneralizationEdge implies UseCaseActorNode ;
UseCaseNoteEdge implies UseCaseNoteNode ;

