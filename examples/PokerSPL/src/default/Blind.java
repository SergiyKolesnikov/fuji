
/**
 * Blind of the hand.
 */
public  /* nullable_by_default */class  Blind {
	

  /** The big. */
  private /*@ spec_public @*/double big;

	

  /** The small. */
  private /*@ spec_public @*/double small;

	

  /** The ante. */
  private/*@ spec_public @*/double ante;

	

  /**
   * Creates a new blind.
   * @param smallBlind
   *          the size of the small blind.
   * @param bigBlind
   *          the size of the big blind.
   * @param anteSize
   *          the size of ante.
   */
  /*@ public normal_behavior
      requires smallBlind >= 0;
      requires bigBlind >= 0;
      requires anteSize >=0;
      assignable small, big, ante;
      ensures small == smallBlind;
      ensures big == bigBlind;
      ensures ante == anteSize;
    */
  public Blind(final double smallBlind, final double bigBlind,
      final double anteSize) {
    this.small = smallBlind;
    this.big = bigBlind;
    this.ante = anteSize;
  }

	

  

  /**
   * @return the ante
   */
  /*@ public normal_behavior
      requires ante >= 0;
      ensures 0 <= \result;
      ensures ante == \result;
   */
  public final/*@ pure @*/double getAnte() {
    // assert false;
    //assert false;
    return ante;
  }

	

  /**
   * @param newAnte the new ante
   */
  /*@ public normal_behavior
      requires newAnte >= 0;
      assignable ante;
      ensures ante == newAnte;
   */
  public final void setAnte(final double newAnte) {
    this.ante = newAnte;
    // assert false;
    //assert false;
  }

	

  /**
   * @return the big blind
   */
  /*@ public normal_behavior
      requires big >= 0;
      ensures 0 <= \result;
      ensures big == \result;
   */
  public final/*@ pure @*/double getBig() {
    // assert false;
    //assert false;
    return big;
  }

	

  /**
   * @param newBig the new value of big blind.
   */
  /*@ public normal_behavior
      requires newBig >= 0;
      assignable big;
      ensures big == newBig;
   */
  public final void setBig(final double newBig) {
    this.big = newBig;
    // assert false;
    //assert false;
  }

	

  /**
   * @return the small
   */
  /*@ public normal_behavior
      requires small >= 0;
      ensures 0 <= \result;
      ensures small == \result;
   */
  public final/*@ pure @*/double getSmall() {
    // assert false;
    //assert false;
    return small;
  }

	

  /**
   * @param newSmall the new small blind value
   */
  /*@ public normal_behavior
      requires newSmall >= 0;
      assignable small;
      ensures small == newSmall;
   */
  public final void setSmall(final double newSmall) {
    this.small = newSmall;
    // assert false;
    //assert false;
  }

	

  


  /**
   * Increase blinds to next level.
   * @param newSmallBlind Next value of small blind.
   * @param newBigBlind Next value of big blind.
   * @param newAnteSize Next value of next ante.
   */
  /*@ public normal_behavior
      requires 0 <= newSmallBlind & small < newSmallBlind;
      requires 0 <= newBigBlind & big < newBigBlind;
      requires 0 <= newAnteSize & ante < newAnteSize;
      assignable big, small, ante;
      ensures small == newSmallBlind;
      ensures big == newBigBlind;
      ensures ante == newAnteSize;
   */
  public final void increaseBlind(final double newSmallBlind,
      final double newBigBlind, final double newAnteSize) {
    setAnte(newAnteSize);
    setSmall(newSmallBlind);
    setBig(newBigBlind);
    // assert false;
    //assert false;
  }


}
