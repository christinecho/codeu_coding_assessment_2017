// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.Stack;

final class MyJSONParser implements JSONParser {

  @Override
  public JSON parse(String in) throws IOException {
    // TODO: implement this
	in = in.trim();
	
	// check that input is valid
	if (!(checkBalance(in) && checkEscapableCharacters(in))) {
		throw new IOException();
	}
	if (!(in.charAt(0) == '{' && in.charAt(in.length()-1) == '}')) {
		throw new IOException("Starting or ending character is not correct");
	}
	if (in.length() < 2) {
		throw new IOException("Length is too small");
	}
	
	
    return new MyJSON();
  }
  
  // Checking if parentheses, brackets, curly brackets, and quotes are balanced
  // Presence of parentheses or brackets in the string was not noted in instructions 
  // but added in code just in case
  private boolean checkBalance(String str) {
	 Stack<Character> stack = new Stack<Character>();
	 for (int i=0; i<str.length()-1; i++) {
		 char currentChar = str.charAt(i);
		 if (currentChar == '{' || currentChar == '(' || currentChar == '[') {
			 stack.push(currentChar);
		 } else if (currentChar == '}') {
			 if (!stack.isEmpty() && stack.pop() == '{') {
				 return true;
			 }
		 } else if (currentChar == ')') {
			 if (!stack.isEmpty() && stack.pop() == '(') {
				 return true;
			 }
		 } else if (currentChar == '[') {
			 if (!stack.isEmpty() && stack.pop() == ']') {
				 return true;
			 }
		 }
	 }
	 
	 if (stack.isEmpty()) {
		 return true;
	 }
	 return false;
  }
  
  private boolean checkEscapableCharacters(String str) {
	  for (int i=0; i<str.length()-1; i++) {
		  char currentChar = str.charAt(i);
		  if (currentChar == '\\') {
			  currentChar = str.charAt(i+1);
			  if (currentChar != 't' && currentChar != 'n') {
				  return false;
			  }
		  }
	  }
	  return true;
  }
}
