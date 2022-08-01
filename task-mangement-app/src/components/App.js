import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { uuid } from "uuidv4";
import api from "../api/task";
import "./App.css";
import Header from "./Header";
import AddTask from "./AddTask"
import TaskList from "./TaskList";
import TaskDetail from "./TaskDetail";
import EditTask from "./EditTask";

function App() {
  const LOCAL_STORAGE_KEY = "tasks";
  const [tasks, setTasks] = useState([]);

  //RetrieveContacts
  const retrieveTasks = async () => {
    const response = await api.get("/task");
    return response.data;
  };

  const addTaskHandler = async (task) => {
    console.log(task);
    const request = {
      ...task,
    };

    const response = await api.post("/task", request);
    console.log(response);
    const allTasks = await retrieveTasks();
    if (allTasks) setTasks(allTasks);
  };

  const updateTaskHandler = async (task) => {
    const response = await api.put(`/task/${task.id}`, task);
    const { id, description, taskDate } = response.data;
    setTasks(
      task.map((task) => {
        return task.id === id ? { ...response.data } : task;
      })
    );
  };

  const removeTaskHandler = async (id) => {
    await api.delete(`/task/${id}`);
    const newTaskList = tasks.filter((task) => {
      return task.id !== id;
    });

    setTasks(newTaskList);
  };

  useEffect(() => {
    // const retriveContacts = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
    // if (retriveContacts) setContacts(retriveContacts);
    const getAllTasks = async () => {
      const allTasks = await retrieveTasks();
      if (allTasks) setTasks(allTasks);
    };

    getAllTasks();
  }, []);

  useEffect(() => {
    //localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(contacts));
  }, [tasks]);

  return (
    <div className="ui container">
      <Router>
        <Header />
        <Switch>
          <Route
            path="/"
            exact
            render={(props) => (
              <TaskList
                {...props}
                tasks={tasks}
                getTaskId={removeTaskHandler}
              />
            )}
          />
          <Route
            path="/add"
            render={(props) => (
              <AddTask {...props} addTaskHandler={addTaskHandler} />
            )}
          />

          <Route
            path="/edit"
            render={(props) => (
              <EditTask
                {...props}
                updateTaskHandler={updateTaskHandler}
              />
            )}
          />

          <Route path="/task/:id" component={TaskDetail} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
