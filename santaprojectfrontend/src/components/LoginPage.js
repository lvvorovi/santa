import React, { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../AuthContext";
import { Button, Form, Grid, Segment } from "semantic-ui-react";
import { jwtDecode } from "jwt-decode";

export function getUsername() {
  const token = localStorage.getItem("token");
  if (token !== null) {
    const decoded = jwtDecode(token);
    return decoded.sub;
  }
  return null;
}

export function LoginPage() {
  const [user, setUser] = useState({});
  const [userId, setUserId] = useState("");
  const [error, setError] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const { appState, setAppState } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleRegisterClick = () => {
    navigate(`/auth/register`);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!appState.isAuthenticated) {
      setAppState({ type: "LOADING", value: true });

      const credentials = {
        email: email,
        password: password,
      };

      try {
        const response = await fetch("/api/v1/auth/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(credentials),
        });

        if (response.ok) {
          const data = await response.json();
          console.log("DATA:", data);
          localStorage.setItem("token", data.access_token);
          console.log("ACCESS TOKEN", data.access_token);
          console.log("LOCAL STORAGE", localStorage);
          
          setAppState({ type: "LOGIN", value: true });
          console.log("USER ID", data.user.userId);
          navigate(`/users/${data.user.userId}`);
        } else {
          setError("Login failed. Please try again.");
        }
      } catch (error) {
        setError("An error occurred. Please try again later.");
      } finally {
        setAppState({ type: "LOADING", value: false });
      }
    } else {
      alert("You are already logged in");
      setAppState({ type: "AUTHENTICATED", value: true });
    }
  };

  const fetchUser = async () => {
    try {
      const email = getUsername();
      const response = await fetch(`/api/v1/users/${email}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const jsonResponse = await response.json();
      setUser(jsonResponse);

      console.log("Fetched User:", jsonResponse);
    } catch (error) {
      console.error("Error fetching user:", error);
    }
  };

  // Add useEffect to automatically fetch user data after login
  useEffect(() => {
    if (appState.isAuthenticated) {
      fetchUser();
    }
  }, [appState.isAuthenticated]);

  return (
    <Grid centered columns={2}>
      <Grid.Column>
        <h2>Log in</h2>
        <Segment style={{ backgroundColor: "rgb(250, 110, 110" }} inverted>
          <Form style={{ backgroundColor: "rgb(250, 110, 110" }} inverted>
            <Form.Group widths="equal">
              <Form.Field>
                <label>Email address</label>
                <input
                  type="email"
                  name="email"
                  placeholder="Email"
                  value={email}
                  onChange={handleEmailChange}
                />
              </Form.Field>
              <Form.Field>
                <label>Password</label>
                <input
                  type="password"
                  name="password"
                  placeholder="Password"
                  value={password}
                  onChange={handlePasswordChange}
                />
              </Form.Field>
            </Form.Group>
            <Button
              fluid
              type="submit"
              color="white"
              inverted
              onClick={handleSubmit}
            >
              Login
            </Button>
            <Button
              className="create mt-2"
              color="white"
              inverted
              fluid
              onClick={handleRegisterClick}
            >
              Register
            </Button>
          </Form>
        </Segment>
      </Grid.Column>
    </Grid>
  );
}
