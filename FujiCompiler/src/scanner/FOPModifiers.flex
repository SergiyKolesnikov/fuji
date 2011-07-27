// FOP Modifiers
<YYINITIAL> {
  "feature"                        { return sym(Terminals.FEATURE); }
  "subsequent"                     { return sym(Terminals.SUBSEQUENT); }
  "program"                        { return sym(Terminals.PROGRAM); }
}
