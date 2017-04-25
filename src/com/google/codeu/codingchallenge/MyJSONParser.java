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

/*
 * Code to parse a JSON-lite object. 
 * Unfortunately I was out of town and could only start tonight so this is not done, 
 * but I am willing to learn and improve from my mistakes.
 */
package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.Stack;

final class MyJSONParser implements JSONParser {

  @Override
  public JSON parse(String in) throws IOException {
	if(in == "{ }") {
		return new MyJSON();
	}
	in = in.trim();
	JSON json = new MyJSON();
	
	// check that input is valid
	if (!(checkBalance(in) && checkEscapableCharacters(in))) {
		throw new IOException();
	}
	if (!(in.charAt(0) == '{' && in.charAt(in.length()-1) == '}')) {
		throw new IOException("Starting and/or ending character does not end in correct bracket");
	}
	if (in.length() < 2) {
		throw new IOException("Length is too short");
	}
	
	//method is not complete and still needs debugging but trying to get key-value pairs
	String s = in.substring(1, in.length()-1).trim();
	String[] mems = s.split(",");
	for(int i=0; i<mems.length; i++) {
		String str = mems[i].trim();
		while (i<mems.length && str.contains("{") && !str.contains("}")) {
			s += ",";
			s += mems[++i];
		}
		
		if (checkPair(str)) {
			int colonIndex = str.indexOf(':');
			String key = str.substring(1,colonIndex-1); //don't want the beginning or end characters
			String value = str.substring(colonIndex+1, str.length());
			if (checkObject(value)) {
				json.setObject(key, new MyJSONParser().parse(value));
			} else {
				value = value.substring(1,value.length()-1);
				json.setString(key, value);
			}
		}
	}
    return json;
  }
  
  private boolean checkPair(String str) {
	  str = str.trim();
	  int colonInd = str.indexOf(":");
	  String key = str.substring(0, colonInd);
	  String val = str.substring(colonInd+1, str.length()).trim();
	  
	  if (val.charAt(0) == '{' && !checkObject(val)) {
		  return false;
	  } else if (val.charAt(0) != '{' && !checkString(val)) {
		  return false;
	  } return checkString(key);
  }
  
  private boolean checkObject(String str) {
	  if (str.charAt(0) != '{' || str.charAt(str.length()-1) != '}') {
		  return false;
	  }
	  return true;
  }
  
  private boolean checkString(String str) {
	  str.trim();
	  if (!checkEscapableCharacters(str)) {
		  return false;
	  } else if (str.charAt(0) != '\"' || str.charAt(str.length()-1) != '\"') {
		  return false;
	  } return true;
  }
  /* Checking if parentheses, square brackets, and curly brackets are balanced
  	 Presence of parentheses or brackets in the string was not noted in instructions 
     but added in code just in case
  */
  private boolean checkBalance(String str) {
	 Stack<Character> stack = new Stack<Character>();
	 for (int i=0; i<str.length(); i++) {
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
	  int quoteCount = 0;
	  for (int i=0; i<str.length()-1; i++) {
		  char currentChar = str.charAt(i);
		  if (currentChar == '\\') {
			  currentChar = str.charAt(i+1);
			  if (currentChar != 't' && currentChar != 'n') {
				  return false;
			  }
		  } else if (currentChar == '"') {
			  quoteCount++;
		  }
	  }
	  return quoteCount % 2 == 0;
  }
  
  //not sure if this method is necessary now
  private String deleteWhiteSpaces(String str) {
	  StringBuilder stringBuilder = new StringBuilder();
	  for (int i=0; i<str.length(); i++) {
		  char currentChar = str.charAt(i);
		  if (currentChar != ' ') {
			  stringBuilder.append(currentChar);
		  }
	  }
	  return stringBuilder.toString();
  }
}
