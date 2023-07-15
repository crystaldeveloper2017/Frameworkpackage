package com.crystal.CustomExceptions;
public class CustomerMobileAlreadyExist extends Exception
{
  public CustomerMobileAlreadyExist(String message)
  {
    super(message);
  }
}