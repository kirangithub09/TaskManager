
import { useState } from 'react'

import { getTasks, registerUser, loginUser } from './api'

import './App.css'

function App() {
  // State variables
  const [page, setPage] = useState('login')
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [message, setMessage] = useState('')
  const [isLoading, setIsLoading] = useState(false)

  const [tasks, setTasks] = useState([])
  const [loggedInUser, setLoggedInUser] = useState(() => {
    try {
      const savedUser = localStorage.getItem('user')
      return savedUser ? JSON.parse(savedUser) : null
    } catch {
      return null
    }
  })

  const isRegister = page === 'register'

  // Handle registration and login
  async function handleSubmit(event) {
    event.preventDefault()
    setMessage('')
    setIsLoading(true)

    try {
      if (isRegister) {
        const userData = {
          username: username,
          email: email,
          password: password,
        }

        await registerUser(userData)

        setPage('login')
        setUsername('')
        setEmail('')
        setPassword('')
        setMessage('Account created successfully! Please sign in.')
      } else {
        const credentials = {
          username: email,
          password: password,
        }

        const data = await loginUser(credentials)

        localStorage.setItem('token', data.token)
        localStorage.setItem('user', JSON.stringify(data.user))

        setLoggedInUser(data.user)
        setPage('dashboard')
        setMessage('Loading your tasks...')

        const taskData = await getTasks(data.token)
        setTasks(taskData)
        setMessage('')
      }
    } catch (error) {
      setMessage(error.message || 'Something went wrong. Please try again.')
    } finally {
      setIsLoading(false)
    }
  }

  // Switch between login and registration
  function switchPage(nextPage) {
    setPage(nextPage)
    setUsername('')
    setEmail('')
    setPassword('')
    setMessage('')
  }

  // Log out the current user
  function handleLogout() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')

    setLoggedInUser(null)
    setTasks([])
    setUsername('')
    setEmail('')
    setPassword('')
    setMessage('')
    setPage('login')
  }

  // Dashboard page: now styled with Tailwind CSS
  if (page === 'dashboard') {
    return (
      <main className="grid min-h-screen grid-cols-1 bg-[#f7f8fc] font-sans text-[#20243a] min-[801px]:grid-cols-2">
        {/* Dashboard introduction panel */}
        <section className="flex flex-col justify-between bg-[#eeedff] px-[7%] py-7 min-[801px]:px-[9%] min-[801px]:py-11">
          <p className="m-0 text-[28px] font-bold text-[#635bdb]">
            TaskFlow
          </p>

          <div className="my-[45px] mb-[25px] max-w-[520px] min-[801px]:my-[55px]">
            <p className="text-xs font-bold tracking-[1.5px] text-[#635bdb]">
              YOUR PERSONAL WORKSPACE
            </p>

            <h1 className="my-5 text-4xl leading-[1.12] min-[801px]:text-[clamp(36px,4vw,56px)]">
              Make room for the work that matters.
            </h1>

            <p className="text-[17px] leading-[1.8] text-[#686d84]">
              Organize your tasks, track your progress, and make
              every day a little more productive.
            </p>
          </div>

          <p className="mb-0 text-[13px] text-[#686d84]">
            Plan your work. Make progress. Get things done.
          </p>
        </section>

        {/* Dashboard task panel */}
        <section className="grid place-items-center bg-white px-6 py-10">
          <div className="w-full max-w-[560px]">
            <p className="mb-3 text-xs font-bold tracking-[1.5px] text-[#635bdb]">
              YOUR WORKSPACE
            </p>

            <h2 className="mb-3 text-[30px] font-bold leading-[1.3]">
              Welcome, {loggedInUser?.username || 'there'}! 🎉
            </h2>

            <p className="mb-8 text-sm leading-[1.7] text-[#777d94]">
              Here are your tasks from the database.
            </p>

            {message && (
              <p
                className="mb-5 rounded-lg bg-[#eeedff] p-3 text-[13px] leading-[1.6] text-[#5048c6]"
                role="status"
              >
                {message}
              </p>
            )}

            {tasks.length === 0 && !message ? (
              <div className="rounded-xl border border-dashed border-[#dfe1ec] bg-[#f7f8fc] p-6 text-center">
                <p className="mb-2 text-lg font-bold">
                  No tasks yet
                </p>

                <p className="text-sm leading-[1.7] text-[#777d94]">
                  Your workspace is ready. We'll add task creation
                  in the next mission.
                </p>
              </div>
            ) : (
              <div className="flex flex-col gap-4">
                {tasks.map((task) => (
                  <article
                    key={task.id}
                    className="rounded-xl border border-[#e5e7f0] bg-white p-5 shadow-sm transition-shadow hover:shadow-md"
                  >
                    <h3 className="mb-2 break-words text-lg font-bold">
                      {task.title}
                    </h3>

                    <p className="mb-4 break-words text-sm leading-[1.7] text-[#686d84]">
                      {task.description || 'No description'}
                    </p>

                    <div className="flex flex-col gap-2 text-sm">
                      <p>
                        <span className="font-semibold">Status:</span>{' '}
                        {task.status}
                      </p>

                      <p>
                        <span className="font-semibold">Priority:</span>{' '}
                        {task.priority}
                      </p>

                      <p>
                        <span className="font-semibold">Due date:</span>{' '}
                        {task.dueDate || 'Not set'}
                      </p>
                    </div>
                  </article>
                ))}
              </div>
            )}

            <button
              className="mt-6 w-full rounded-[9px] bg-[#635bdb] p-[14px] font-bold text-white transition-colors hover:bg-[#5048c6] focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-[#635bdb]"
              type="button"
              onClick={handleLogout}
            >
              Sign Out
            </button>
          </div>
        </section>
      </main>
    )
  }

  // Login and registration pages: styled with Tailwind CSS
  return (
    <main className="grid min-h-screen grid-cols-1 bg-[#f7f8fc] font-sans text-[#20243a] min-[801px]:grid-cols-2">
      <section className="flex flex-col justify-between bg-[#eeedff] px-[7%] py-7 min-[801px]:px-[9%] min-[801px]:py-11">
        <p className="m-0 text-[28px] font-bold text-[#635bdb]">
          TaskFlow
        </p>

        <div className="my-[45px] mb-[25px] max-w-[520px] min-[801px]:my-[55px]">
          <p className="text-xs font-bold tracking-[1.5px] text-[#635bdb]">
            YOUR PERSONAL WORKSPACE
          </p>

          <h1 className="my-5 text-4xl leading-[1.12] min-[801px]:text-[clamp(36px,4vw,56px)]">
            Make room for the work that matters.
          </h1>

          <p className="text-[17px] leading-[1.8] text-[#686d84]">
            Organize your tasks, track your progress, and make
            every day a little more productive.
          </p>
        </div>

        <p className="mb-0 text-[13px] text-[#686d84]">
          Plan your work. Make progress. Get things done.
        </p>
      </section>

      <section className="grid place-items-center bg-white px-6 py-10">
        <div className="w-full max-w-[420px]">
          <p className="mb-3 text-xs font-bold tracking-[1.5px] text-[#635bdb]">
            {isRegister ? 'GET STARTED' : 'WELCOME BACK'}
          </p>

          <h2 className="mb-3 text-[30px] font-bold leading-[1.3]">
            {isRegister ? 'Create your account' : 'Sign in to TaskFlow'}
          </h2>

          <p className="mb-[30px] text-sm leading-[1.7] text-[#777d94]">
            {isRegister
              ? 'Create an account to start organizing your tasks.'
              : 'Enter your details to access your workspace.'}
          </p>

          <form onSubmit={handleSubmit}>
            {isRegister && (
              <div className="mb-5 flex flex-col gap-[9px]">
                <label className="text-sm font-bold" htmlFor="username">
                  Username
                </label>

                <input
                  className="w-full rounded-[9px] border border-[#dfe1ec] bg-white p-[14px] outline-none focus:border-[#635bdb] focus:ring-[3px] focus:ring-[#635bdb]/15"
                  id="username"
                  type="text"
                  placeholder="Choose a username"
                  value={username}
                  onChange={(event) => setUsername(event.target.value)}
                  minLength={3}
                  required
                />
              </div>
            )}

            <div className="mb-5 flex flex-col gap-[9px]">
              <label className="text-sm font-bold" htmlFor="email">
                {isRegister ? 'Email address' : 'Username'}
              </label>

              <input
                className="w-full rounded-[9px] border border-[#dfe1ec] bg-white p-[14px] outline-none focus:border-[#635bdb] focus:ring-[3px] focus:ring-[#635bdb]/15"
                id="email"
                type={isRegister ? 'email' : 'text'}
                placeholder={
                  isRegister ? 'you@example.com' : 'Enter your username'
                }
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                autoComplete={isRegister ? 'email' : 'username'}
                required
              />
            </div>

            <div className="mb-5 flex flex-col gap-[9px]">
              <label className="text-sm font-bold" htmlFor="password">
                Password
              </label>

              <input
                className="w-full rounded-[9px] border border-[#dfe1ec] bg-white p-[14px] outline-none focus:border-[#635bdb] focus:ring-[3px] focus:ring-[#635bdb]/15"
                id="password"
                type="password"
                placeholder="Enter your password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                autoComplete={
                  isRegister ? 'new-password' : 'current-password'
                }
                minLength={isRegister ? 8 : undefined}
                required
              />

              {isRegister && (
                <small className="text-xs text-[#777d94]">
                  Use at least 8 characters.
                </small>
              )}
            </div>

            <button
              className="mt-1 w-full rounded-[9px] bg-[#635bdb] p-[14px] font-bold text-white transition-colors hover:bg-[#5048c6] disabled:cursor-not-allowed disabled:opacity-60"
              type="submit"
              disabled={isLoading}
            >
              {isLoading
                ? 'Please wait...'
                : isRegister
                  ? 'Create Account'
                  : 'Sign In'}
            </button>
          </form>

          {message && (
            <p
              className="rounded-lg bg-[#eeedff] p-3 text-[13px] leading-[1.6] text-[#5048c6]"
              role="status"
            >
              {message}
            </p>
          )}

          <div className="mt-[26px] flex flex-wrap justify-center gap-1.5 text-sm text-[#777d94]">
            {isRegister
              ? 'Already have an account?'
              : "Don't have an account?"}

            <button
              className="border-0 bg-transparent p-0 font-bold text-[#635bdb]"
              type="button"
              onClick={() => switchPage(isRegister ? 'login' : 'register')}
            >
              {isRegister ? 'Sign in' : 'Create account'}
            </button>
          </div>
        </div>
      </section>
    </main>
  )
}

export default App
